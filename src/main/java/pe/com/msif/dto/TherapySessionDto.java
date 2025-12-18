package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TherapySessionDto {
    private Long id;
    private Long treatmentPlanId;
    private Long appointmentId;
    private LocalDateTime attendanceDate;
    private String note;
    private Boolean isActive;
}
