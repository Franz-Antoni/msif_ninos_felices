package pe.com.msif.dto;

import lombok.Data;

@Data
public class RecommendationDto {
    private Long id;
    private String name;
    private String description;
    private Long medicalHistoryId;
    private Boolean isActive;
}
