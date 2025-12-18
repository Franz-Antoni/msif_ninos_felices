package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.msif.dto.ProfessionalWithSpecialtyDto;
import pe.com.msif.model.Professional;
import pe.com.msif.model.Specialty;
import pe.com.msif.repository.SpecialtyRepository;
import pe.com.msif.service.ProfessionalService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profesionales")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @GetMapping
    public List<ProfessionalWithSpecialtyDto> listProfessionals() {
        List<Professional> professionals = professionalService.FindAllByStatus(true);

        // Cargar especialidades en un mapa para evitar N+1
        Map<Long, Specialty> specialties = specialtyRepository.findAll().stream()
                .collect(Collectors.toMap(Specialty::getId, s -> s));

        return professionals.stream().map(p -> {
            Specialty sp = specialties.get(p.getSpecialtyId());
            String specialtyName = sp != null ? sp.getName() : null;
            return new ProfessionalWithSpecialtyDto(
                    p.getId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getPhone(),
                    p.getIsActive(),
                    p.getSpecialtyId(),
                    specialtyName
            );
        }).collect(Collectors.toList());
    }
}

