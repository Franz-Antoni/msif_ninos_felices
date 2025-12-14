package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenService {

    private static final String PREFIX = "refresh:";

    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private RefreshTokenGenerator generator;

    @Value("${spring.security.jwt.refresh-token.expiration}")
    private long expiration;

    public String create(String username) {

        String token = generator.generate();

        redis.opsForValue().set(
                PREFIX + token,
                username,
                expiration,
                TimeUnit.MILLISECONDS
        );

        return token;
    }

    public String validate(String token) {
        return redis.opsForValue().get(PREFIX + token);
    }

    public void revoke(String token) {
        redis.delete(PREFIX + token);
    }
}
