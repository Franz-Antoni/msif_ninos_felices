package pe.com.msif.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginDto {
    private String email;
    private String password;
}