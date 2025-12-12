package pe.com.msif.service;

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
import pe.com.msif.repository.CitaRepository;
import pe.com.msif.repository.PatientRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private static final Logger log = LoggerFactory.getLogger(CitaService.class);

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AutoMapper autoMapper;

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
    public Cita findById(Integer id) {
        Optional<Cita> optional = citaRepository.findById(id);
        if (optional.isEmpty()) throw new NotFoundException("Cita con id " + id + " no encontrada");
        return optional.get();
    }

    @Transactional
    public Cita update(Integer id, UpdateCitaDto dto) {
        Cita cita = findById(id);

        if (dto.getRazon() != null) cita.setRazon(dto.getRazon());
        if (dto.getProfesionalId() != null) {
            // Si se asigna profesional al actualizar, validar conflicto horario del profesional
            List<Cita> conflictos = citaRepository.findByProfesionalId(dto.getProfesionalId());
            for (Cita c : conflictos) {
                if (!c.getId().equals(cita.getId()) && c.getFechaProgramada().isEqual(dto.getFechaProgramada() != null ? dto.getFechaProgramada() : cita.getFechaProgramada()) && Boolean.TRUE.equals(c.getEstaActivo())) {
                    throw new ConflictException("Existe otra cita para el profesional en la misma fecha y hora.");
                }
            }
            cita.setProfesionalId(dto.getProfesionalId());
            // Si el cliente no envió explícitamente estado y la cita estaba pendiente, marcar confirmada
            if (dto.getEstado() == null && cita.getEstado() == Cita.Estado.PENDIENTE) {
                cita.setEstado(Cita.Estado.CONFIRMADA);
            }
        }
        if (dto.getFechaProgramada() != null) cita.setFechaProgramada(dto.getFechaProgramada());
        if (dto.getEstado() != null) {
            try {
                // Convertir texto libre a enum buscando el dbValue
                Cita.Estado estado = null;
                for (Cita.Estado e : Cita.Estado.values()) {
                    if (e.getDbValue().equalsIgnoreCase(dto.getEstado())) {
                        estado = e;
                        break;
                    }
                }
                if (estado == null) {
                    // intentar por nombre del enum
                    estado = Cita.Estado.valueOf(dto.getEstado().toUpperCase().replace(" ", "_").replace("Á","A").replace("Í","I").replace("Ó","O").replace("É","E").replace("Ú","U"));
                }
                cita.setEstado(estado);
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("estado no reconocido: " + dto.getEstado());
            }
        }
        if (dto.getEstaActivo() != null) cita.setEstaActivo(dto.getEstaActivo());

        return citaRepository.save(cita);
    }

    @Transactional(readOnly = true)
    public Page<Cita> findAll(Optional<Integer> pacienteId, Optional<Integer> profesionalId, Optional<String> estadoOpt, Optional<LocalDateTime> fromOpt, Optional<LocalDateTime> toOpt, Pageable pageable) {
        // Implementación simple: filtrar en memoria usando repo básicos; para producción usar Specifications
        List<Cita> all = citaRepository.findAll();

        List<Cita> filtered = all.stream().filter(c -> {
            if (pacienteId.isPresent() && !pacienteId.get().equals(c.getPacienteId())) return false;
            if (profesionalId.isPresent() && (c.getProfesionalId() == null || !profesionalId.get().equals(c.getProfesionalId()))) return false;
            if (estadoOpt.isPresent()) {
                Cita.Estado e = c.getEstado();
                if (e == null || !e.getDbValue().equalsIgnoreCase(estadoOpt.get())) return false;
            }
            if (fromOpt.isPresent() && c.getFechaProgramada().isBefore(fromOpt.get())) return false;
            if (toOpt.isPresent() && c.getFechaProgramada().isAfter(toOpt.get())) return false;
            return true;
        }).toList();

        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        List<Cita> content = filtered.subList(start, end);
        return new PageImpl<>(content, pageable, filtered.size());
    }

    @Transactional
    public void delete(Integer id) {
        Cita cita = findById(id);
        cita.setEstaActivo(false);
        citaRepository.save(cita);
    }

    @Transactional
    public Cita activate(Integer id) {
        Cita cita = findById(id);
        cita.setEstaActivo(true);
        return citaRepository.save(cita);
    }
}
