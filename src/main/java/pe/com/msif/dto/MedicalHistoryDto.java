package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalHistoryDto {
    private Long id;
    private Long patientId;
    private Long professionalId;
    private LocalDateTime createdAt;
    private String description;
    private String diagnosis;
    private Boolean isActive;
}
