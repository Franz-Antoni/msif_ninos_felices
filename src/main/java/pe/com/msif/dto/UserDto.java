package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private Long guardianId;
    private Long professionalId;
    private LocalDateTime createdAt;
    private Boolean isActive;
}
