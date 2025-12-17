package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TreatmentPlanDto {
    private Integer id;
    private Integer patientId;
    private Integer professionalId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String evaluation;
    private Boolean isActive;
}

