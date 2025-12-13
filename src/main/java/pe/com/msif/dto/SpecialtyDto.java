package pe.com.msif.dto;

import lombok.Data;

@Data
public class SpecialtyDto {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
}

