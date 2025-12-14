package pe.com.msif.dto;

public record AuthResponse (
    String accessToken,
    String refreshToken
){}
