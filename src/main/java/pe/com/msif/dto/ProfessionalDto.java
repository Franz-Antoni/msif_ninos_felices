package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfessionalDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer specialtyId;
    private String phone;
    private LocalDateTime registrationDate;
    private Boolean isActive;
}

