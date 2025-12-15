package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.msif.dto.AuthResponse;
import pe.com.msif.exception.ConflictException;
import pe.com.msif.model.Guardian;
import pe.com.msif.model.User;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private GuardianService guardianService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Transactional
    public User Save(User user, Guardian guardian) {
        Optional<User> userExists = userService.FindByEmail(user.getEmail());

        if(userExists.isPresent()) {
            throw new ConflictException("Este correo ya esta registrado.");
        }

        Optional<Guardian> guardianExists = guardianService.FindByDni(guardian.getDni());

        if(guardianExists.isEmpty()) {
            guardianExists = Optional.ofNullable(guardianService.Save(guardian));
        }

        user.setGuardianId(guardianExists.get().getId());

        return userService.Save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(String email, String password) {

        User user = userService.FindByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new RuntimeException("Usuario inactivo");
        }

        // Temporal: sin BCrypt aún
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String accessToken = jwtService.generateAccessToken(email);
        String refreshToken = refreshTokenService.create(email);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse refresh(String refreshToken) {

        String email = refreshTokenService.validate(refreshToken);

        if (email == null) {
            throw new RuntimeException("Refresh token inválido");
        }

        refreshTokenService.revoke(refreshToken);

        String newAccessToken = jwtService.generateAccessToken(email);
        String newRefreshToken = refreshTokenService.create(email);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

}
