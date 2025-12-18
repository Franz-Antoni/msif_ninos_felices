package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TreatmentPlanDto {
    private Long id;
    private Long patientId;
    private Long professionalId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String evaluation;
    private Boolean isActive;
}
