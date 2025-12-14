package pe.com.msif.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.security.jwt.secret-key}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private long accessExpiration;

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    public String generateAccessToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessExpiration))
                .sign(algorithm());
    }

    public String extractUsername(String token) {
        return JWT.require(algorithm())
                .build()
                .verify(token)
                .getSubject();
    }
}