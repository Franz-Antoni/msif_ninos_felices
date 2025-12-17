package pe.com.msif.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class RefreshTokenGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder encoder =
            Base64.getUrlEncoder().withoutPadding();

    public String generate() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return encoder.encodeToString(bytes);
    }
}
