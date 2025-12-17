package pe.com.msif.dto;

import lombok.Data;

@Data
public class AuthGuardianDto {
    private GuardianDto guardian;
    private UserDto user;
}
