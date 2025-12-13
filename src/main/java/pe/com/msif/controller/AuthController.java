package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.AuthDto;
import pe.com.msif.dto.AuthGuardianDto;
import pe.com.msif.dto.GuardianDto;
import pe.com.msif.dto.UserDto;
import pe.com.msif.model.Guardian;
import pe.com.msif.model.User;
import pe.com.msif.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthDto> Create(@RequestBody AuthGuardianDto authGuardianDto) {

        User user = autoMapper.mapTo(authGuardianDto.getUser(), User.class);
        Guardian guardian = autoMapper.mapTo(authGuardianDto.getGuardian(), Guardian.class);

        User userResponse = authService.Save(user, guardian);

        AuthDto authResponse = new AuthDto(
                userResponse.getEmail(),
                "Usuario creado con exito, bienvenido al sistema."
        );

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}
