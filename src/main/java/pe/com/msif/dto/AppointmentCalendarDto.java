package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentCalendarDto {
    private Long id;
    private Integer year;
    private Integer month;
    private Integer day;
    private String reason;
    private Long patientId;
    private String patientName;
    private String status;
    private Boolean isActive;
    private LocalDateTime scheduledDate;
}
