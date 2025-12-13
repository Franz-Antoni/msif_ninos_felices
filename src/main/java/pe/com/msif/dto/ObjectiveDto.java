package pe.com.msif.dto;

import lombok.Data;

@Data
public class ObjectiveDto {
    private Integer id;
    private String title;
    private String description;
    private Integer treatmentPlanId;
    private String status;
    private Boolean isActive;
}

