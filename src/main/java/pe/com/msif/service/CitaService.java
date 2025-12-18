package pe.com.msif.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.CreateCitaDto;
import pe.com.msif.dto.UpdateCitaDto;
import pe.com.msif.exception.BadRequestException;
import pe.com.msif.exception.ConflictException;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Cita;
import pe.com.msif.model.Patient;
import pe.com.msif.model.Professional;
import pe.com.msif.repository.CitaRepository;
import pe.com.msif.repository.PatientRepository;
import pe.com.msif.repository.ProfessionalRepository;
import pe.com.msif.repository.MedicalHistoryRepository;
import pe.com.msif.repository.TreatmentPlanRepository;
import pe.com.msif.repository.TherapySessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private static final Logger log = LoggerFactory.getLogger(CitaService.class);

    @PostConstruct
    public void debugDb() {
        System.out.println("TOTAL CITAS = " + citaRepository.count());
    }


    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AutoMapper autoMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @Autowired
    private TherapySessionRepository therapySessionRepository;

    @Transactional
    public Cita create(CreateCitaDto dto) {
        // LOG: imprimir payload recibido para depuración
        try {
            log.info("CreateCita received - razon='{}' pacienteDni='{}' pacienteId='{}' fechaProgramada='{}'", dto.getRazon(), dto.getPacienteDni(), dto.getPacienteId(), dto.getFechaProgramada());
        } catch (Exception ex) {
            // no queremos que logging rompa la lógica
            log.warn("Error al crear log del DTO de createCita: {}", ex.getMessage());
        }
        // Validaciones
        if (dto.getFechaProgramada() == null) throw new BadRequestException("fechaProgramada es requerida");
        // Compatibilidad: priorizar pacienteDni, si no está usar pacienteId (clientes antiguos)
        Patient paciente = null;
        if (dto.getPacienteDni() != null && !dto.getPacienteDni().isBlank()) {
            Optional<Patient> optPaciente = patientRepository.findByDni(dto.getPacienteDni());
            if (optPaciente.isEmpty()) {
                throw new NotFoundException("El dni del paciente no está registrado");
            }
            paciente = optPaciente.get();
        } else if (dto.getPacienteId() != null) {
            Optional<Patient> optPaciente = patientRepository.findById(dto.getPacienteId());
            if (optPaciente.isEmpty()) {
                throw new NotFoundException("Paciente con id " + dto.getPacienteId() + " no existe");
            }
            paciente = optPaciente.get();
        } else {
            throw new BadRequestException("dni del paciente es requerido");
        }

        // Validar razon
        if (dto.getRazon() == null || dto.getRazon().isBlank()) throw new BadRequestException("razon es requerida");

        Cita cita = new Cita();
        cita.setRazon(dto.getRazon());
        cita.setPacienteId(paciente.getId());
        // No asignar profesionalId en la creación; la cita queda sin profesional hasta que se apruebe
        cita.setProfesionalId(null);
        cita.setFechaProgramada(dto.getFechaProgramada());
        cita.setFechaCreacion(LocalDateTime.now());
        cita.setEstaActivo(true);
        cita.setEstado(Cita.Estado.PENDIENTE);

        return citaRepository.save(cita);
    }

    @Transactional(readOnly = true)
    public Cita findById(Long id) {
        Optional<Cita> optional = citaRepository.findById(id);
        if (optional.isEmpty()) throw new NotFoundException("Cita con id " + id + " no encontrada");
        return optional.get();
    }

    @Transactional
    public Cita update(Long id, UpdateCitaDto dto) {
        Cita cita = findById(id);

        if (dto.getRazon() != null) cita.setRazon(dto.getRazon());

        if (dto.getProfesionalId() != null) {
            List<Cita> conflictos = citaRepository.findByProfesionalId(dto.getProfesionalId());
            for (Cita c : conflictos) {
                if (!c.getId().equals(cita.getId())
                        && c.getFechaProgramada().isEqual(
                        dto.getFechaProgramada() != null
                                ? dto.getFechaProgramada()
                                : cita.getFechaProgramada()
                )
                        && Boolean.TRUE.equals(c.getEstaActivo())) {
                    throw new ConflictException("Existe otra cita para el profesional en la misma fecha y hora.");
                }
            }
            cita.setProfesionalId(dto.getProfesionalId());

            if (dto.getEstado() == null && cita.getEstado() == Cita.Estado.PENDIENTE) {
                cita.setEstado(Cita.Estado.CONFIRMADA);
            }
        }

        if (dto.getFechaProgramada() != null) {
            cita.setFechaProgramada(dto.getFechaProgramada());
        }

        if (dto.getEstado() != null) {
            try {
                String normalized = dto.getEstado()
                        .toUpperCase()
                        .replace(" ", "_")
                        .replace("Á","A")
                        .replace("É","E")
                        .replace("Í","I")
                        .replace("Ó","O")
                        .replace("Ú","U");

                Cita.Estado estado = Cita.Estado.valueOf(normalized);
                cita.setEstado(estado);

            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("estado no reconocido: " + dto.getEstado());
            }
        }

        if (dto.getEstaActivo() != null) {
            cita.setEstaActivo(dto.getEstaActivo());
        }

        return citaRepository.save(cita);
    }

    @Transactional(readOnly = true)
    public Page<Cita> findAll(
            Optional<Long> pacienteId,
            Optional<Long> profesionalId,
            Optional<String> estadoOpt,
            Optional<LocalDateTime> fromOpt,
            Optional<LocalDateTime> toOpt,
            Pageable pageable
    ) {
        List<Cita> all = citaRepository.findAll();

        List<Cita> filtered = all.stream().filter(c -> {
            if (pacienteId.isPresent() && !pacienteId.get().equals(c.getPacienteId())) return false;
            if (profesionalId.isPresent()
                    && (c.getProfesionalId() == null || !profesionalId.get().equals(c.getProfesionalId())))
                return false;

            if (estadoOpt.isPresent()) {
                if (c.getEstado() == null) return false;

                String normalized = estadoOpt.get()
                        .toUpperCase()
                        .replace(" ", "_")
                        .replace("Á","A")
                        .replace("É","E")
                        .replace("Í","I")
                        .replace("Ó","O")
                        .replace("Ú","U");

                if (!c.getEstado().name().equals(normalized)) return false;
            }

            if (fromOpt.isPresent() && c.getFechaProgramada().isBefore(fromOpt.get())) return false;
            if (toOpt.isPresent() && c.getFechaProgramada().isAfter(toOpt.get())) return false;

            return true;
        }).toList();

        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<Cita> content = filtered.subList(start, end);

        return new PageImpl<>(content, pageable, filtered.size());
    }


    @Transactional
    public void delete(Long id) {
        Cita cita = findById(id);
        cita.setEstaActivo(false);
        citaRepository.save(cita);
    }

    @Transactional
    public Cita activate(Long id) {
        Cita cita = findById(id);
        cita.setEstaActivo(true);
        return citaRepository.save(cita);
    }

    @Transactional
    public Cita updateStatus(Long id, pe.com.msif.dto.StatusUpdateDto dto) {
        Cita cita = findById(id);
        if (dto.getEstado() == null) throw new BadRequestException("estado es requerido");

        String normalized = dto.getEstado()
                .toUpperCase()
                .replace(" ", "_")
                .replace("Á","A")
                .replace("É","E")
                .replace("Í","I")
                .replace("Ó","O")
                .replace("Ú","U");

        // Manejar RECHAZADA/RECHAZAR
        if (normalized.equals("RECHAZADA") || normalized.equals("RECHAZADO")) {
            if (dto.getMotivoRechazo() == null || dto.getMotivoRechazo().isBlank()) {
                throw new BadRequestException("motivoRechazo es requerido para rechazar");
            }
            // Guardamos como CANCELADA en la DB, pero registramos motivo
            cita.setEstado(Cita.Estado.CANCELADA);
            cita.setMotivoRechazo(dto.getMotivoRechazo());
            Cita saved = citaRepository.save(cita);
            notificationService.notifyCitaRejected(saved);
            return saved;
        }

        // Manejar CONFIRMADA
        if (normalized.equals("CONFIRMADA")) {
            Long profesionalId = dto.getProfesionalId();
            if (profesionalId == null) throw new BadRequestException("profesionalId es requerido para confirmar la cita");
            Optional<Professional> profesionalOpt = professionalRepository.findById(profesionalId);
            if (profesionalOpt.isEmpty() || Boolean.FALSE.equals(profesionalOpt.get().getIsActive())) {
                throw new BadRequestException("Profesional no encontrado o inactivo");
            }
            // verificar disponibilidad
            List<Cita> conflictos = citaRepository.findByProfesionalIdAndFechaProgramadaAndEstaActivoTrue(profesionalId, cita.getFechaProgramada());
            for (Cita c : conflictos) {
                if (!c.getId().equals(cita.getId())) throw new ConflictException("Profesional no disponible en ese horario");
            }
            cita.setProfesionalId(profesionalId);
            cita.setEstado(Cita.Estado.CONFIRMADA);
            Cita saved = citaRepository.save(cita);
            notificationService.notifyCitaApproved(saved);
            return saved;
        }

        // Manejar ATENDIDA -> crear registros clínicos en tablas separadas
        if (normalized.equals("ATENDIDA")) {
            if (dto.getMotivoAtencion() == null || dto.getMotivoAtencion().isBlank()) {
                throw new BadRequestException("motivoAtencion es requerido al marcar como Atendida");
            }
            if (dto.getEvaluacionClinica() == null || dto.getEvaluacionClinica().isBlank()) {
                throw new BadRequestException("evaluacionClinica es requerida al marcar como Atendida");
            }

            // Crear MedicalHistory
            pe.com.msif.model.MedicalHistory mh = new pe.com.msif.model.MedicalHistory();
            mh.setPatientId(cita.getPacienteId());
            mh.setProfessionalId(cita.getProfesionalId());
            mh.setDescription(dto.getMotivoAtencion() + "\n" + dto.getEvaluacionClinica());
            mh.setDiagnosis(dto.getDiagnostico());
            mh.setIsActive(true);
            medicalHistoryRepository.save(mh);

            // Crear TreatmentPlan si planIntervencion viene
            pe.com.msif.model.TreatmentPlan tp = null;
            if (dto.getPlanIntervencion() != null && !dto.getPlanIntervencion().isBlank()) {
                tp = new pe.com.msif.model.TreatmentPlan();
                tp.setPatientId(cita.getPacienteId());
                tp.setProfessionalId(cita.getProfesionalId());
                tp.setStartDate(java.time.LocalDate.now());
                tp.setEvaluation(dto.getPlanIntervencion());
                tp.setIsActive(true);
                tp = treatmentPlanRepository.save(tp);
            }

            // Crear TherapySession y vincular al plan (si existe) y cita
            pe.com.msif.model.TherapySession ts = new pe.com.msif.model.TherapySession();
            if (tp != null) ts.setTreatmentPlanId(tp.getId());
            ts.setAppointmentId(cita.getId());
            ts.setAttendanceDate(java.time.LocalDateTime.now());
            ts.setNote(dto.getObservaciones());
            ts.setIsActive(true);
            therapySessionRepository.save(ts);

            // Marcar cita como COMPLETADA (representa que la atención se registró)
            cita.setEstado(Cita.Estado.COMPLETADA);
            Cita saved = citaRepository.save(cita);
            return saved;
        }

        // Intentar mapear otros estados al enum disponible
        try {
            Cita.Estado enumState = Cita.Estado.valueOf(normalized);
            cita.setEstado(enumState);
            return citaRepository.save(cita);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("estado no reconocido: " + dto.getEstado());
        }
    }

    @Transactional(readOnly = true)
    public pe.com.msif.dto.AvailabilityDto checkAvailability(Long profesionalId, LocalDateTime fechaProgramada) {
        pe.com.msif.dto.AvailabilityDto av = new pe.com.msif.dto.AvailabilityDto();
        List<Cita> conflictos = citaRepository.findByProfesionalIdAndFechaProgramadaAndEstaActivoTrue(profesionalId, fechaProgramada);
        av.setAvailable(conflictos.isEmpty());
        av.setConflictingDates(conflictos.stream().map(Cita::getFechaProgramada).toList());
        return av;
    }
}
