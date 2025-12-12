package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.CitaDto;
import pe.com.msif.dto.CreateCitaDto;
import pe.com.msif.dto.UpdateCitaDto;
import pe.com.msif.model.Cita;
import pe.com.msif.model.Patient;
import pe.com.msif.service.CitaService;
import pe.com.msif.repository.PatientRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private AutoMapper autoMapper;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public ResponseEntity<CitaDto> create(@RequestBody CreateCitaDto dto) {
        Cita created = citaService.create(dto);
        CitaDto response = autoMapper.mapTo(created, CitaDto.class);
        if (created.getEstado() != null) response.setEstado(created.getEstado().getDbValue());
        // intentar setear pacienteDni si existe el paciente
        if (created.getPacienteId() != null) {
            Optional<Patient> p = patientRepository.findById(created.getPacienteId());
            p.ifPresent(patient -> response.setPacienteDni(patient.getDni()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> getById(@PathVariable Integer id) {
        Cita cita = citaService.findById(id);
        CitaDto dto = autoMapper.mapTo(cita, CitaDto.class);
        if (cita.getEstado() != null) dto.setEstado(cita.getEstado().getDbValue());
        if (cita.getPacienteId() != null) {
            Optional<Patient> p = patientRepository.findById(cita.getPacienteId());
            p.ifPresent(patient -> dto.setPacienteDni(patient.getDni()));
        }
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDto> update(@PathVariable Integer id, @RequestBody UpdateCitaDto dto) {
        Cita updated = citaService.update(id, dto);
        CitaDto response = autoMapper.mapTo(updated, CitaDto.class);
        if (updated.getEstado() != null) response.setEstado(updated.getEstado().getDbValue());
        if (updated.getPacienteId() != null) {
            Optional<Patient> p = patientRepository.findById(updated.getPacienteId());
            p.ifPresent(patient -> response.setPacienteDni(patient.getDni()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CitaDto>> list(
            @RequestParam Optional<Integer> pacienteId,
            @RequestParam Optional<Integer> profesionalId,
            @RequestParam Optional<String> estado,
            @RequestParam Optional<LocalDateTime> from,
            @RequestParam Optional<LocalDateTime> to,
            Pageable pageable
    ) {
        Page<Cita> page = citaService.findAll(pacienteId, profesionalId, estado, from, to, pageable);
        Page<CitaDto> dtoPage = page.map(c -> {
            CitaDto dto = autoMapper.mapTo(c, CitaDto.class);
            if (c.getEstado() != null) dto.setEstado(c.getEstado().getDbValue());
            if (c.getPacienteId() != null) {
                Optional<Patient> p = patientRepository.findById(c.getPacienteId());
                p.ifPresent(patient -> dto.setPacienteDni(patient.getDni()));
            }
            return dto;
        });
        return ResponseEntity.ok(dtoPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        citaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<CitaDto> activar(@PathVariable Integer id) {
        Cita c = citaService.activate(id);
        CitaDto dto = autoMapper.mapTo(c, CitaDto.class);
        if (c.getEstado() != null) dto.setEstado(c.getEstado().getDbValue());
        if (c.getPacienteId() != null) {
            Optional<Patient> p = patientRepository.findById(c.getPacienteId());
            p.ifPresent(patient -> dto.setPacienteDni(patient.getDni()));
        }
        return ResponseEntity.ok(dto);
    }
}
