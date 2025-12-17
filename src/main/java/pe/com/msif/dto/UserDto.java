package pe.com.msif.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private Integer guardianId;
    private Integer professionalId;
}
