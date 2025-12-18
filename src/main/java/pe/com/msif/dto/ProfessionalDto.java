package pe.com.msif.dto;

import lombok.Data;

@Data
public class ProfessionalDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long specialtyId;
    private String phone;
    private Boolean isActive;
}
