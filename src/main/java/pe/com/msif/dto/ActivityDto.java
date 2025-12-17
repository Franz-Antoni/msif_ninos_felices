package pe.com.msif.dto;

import lombok.Data;

@Data
public class ActivityDto {
    private Integer id;
    private String title;
    private String description;
    private Integer therapySessionId;
    private String status;
    private String progressNote;
    private Boolean isActive;
}

