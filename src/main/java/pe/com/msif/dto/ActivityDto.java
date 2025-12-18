package pe.com.msif.dto;

import lombok.Data;

@Data
public class ActivityDto {
    private Long id;
    private String title;
    private String description;
    private Long therapySessionId;
    private String status;
    private String progressNote;
    private Boolean isActive;
}
