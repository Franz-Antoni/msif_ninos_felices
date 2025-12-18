package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {

    private static final String PREFIX = "refresh:";

    // Redis desactivado temporalmente para pruebas locales.
    // @Autowired
    // private StringRedisTemplate redis;

    // Fallback en memoria para pruebas locales (no distribuido, solo para dev).
    private final Map<String, String> localStore = new ConcurrentHashMap<>();

    @Autowired
    private RefreshTokenGenerator generator;

    @Value("${spring.security.jwt.refresh-token.expiration}")
    @SuppressWarnings("unused")
    private long expiration;

    public String create(String username) {
        String token = generator.generate();
        // Uso local en memoria en lugar de Redis
        localStore.put(PREFIX + token, username);
        return token;
    }

    public String validate(String token) {
        return localStore.get(PREFIX + token);
    }

    public void revoke(String token) {
        localStore.remove(PREFIX + token);
    }
}
