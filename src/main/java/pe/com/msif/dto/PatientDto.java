package pe.com.msif.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {
    private Integer id;
    private String name;
    private String lastName;
    private Boolean gender;
    private String dni;
    private LocalDate birthDate;
    private String autismLevel;
    private Integer guardianId;
    private Boolean isActive;
}
