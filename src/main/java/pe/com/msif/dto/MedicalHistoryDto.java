package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalHistoryDto {
    private Integer id;
    private Integer patientId;
    private Integer professionalId;
    private LocalDateTime creationDate;
    private String description;
    private String diagnosis;
    private Boolean isActive;
}

