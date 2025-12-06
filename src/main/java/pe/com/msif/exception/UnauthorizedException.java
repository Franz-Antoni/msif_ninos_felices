package pe.com.msif.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {

    private static final String DESCRIPTION = "No tiene autorización.";
    private static final String CODE = String.valueOf(HttpStatus.UNAUTHORIZED.value());
    private static final HttpStatus HTTPSTATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException() {
        super(CODE, DESCRIPTION, HTTPSTATUS);
    }

    public UnauthorizedException(String message) {
        super(CODE, DESCRIPTION + " " + message, HTTPSTATUS);
    }
}
