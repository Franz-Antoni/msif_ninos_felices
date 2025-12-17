package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TherapySessionDto {
    private Integer id;
    private Integer treatmentPlanId;
    private Integer appointmentId;
    private LocalDateTime attendanceDate;
    private String note;
    private Boolean isActive;
}

