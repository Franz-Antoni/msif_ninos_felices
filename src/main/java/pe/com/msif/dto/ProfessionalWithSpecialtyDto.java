package pe.com.msif.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalWithSpecialtyDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean isActive;
    private Long specialtyId;
    private String specialtyName;
}

