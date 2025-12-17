package pe.com.msif.dto;

import lombok.Data;

@Data
public class RecommendationDto {
    private Integer id;
    private String name;
    private String description;
    private Integer medicalHistoryId;
    private Boolean isActive;
}

