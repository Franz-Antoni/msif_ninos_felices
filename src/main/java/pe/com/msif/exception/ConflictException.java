package pe.com.msif.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BusinessException {

    private static final String DESCRIPTION = "El registro ya existe.";
    private static final String CODE = String.valueOf(HttpStatus.CONFLICT.value());
    private static final HttpStatus HTTPSTATUS = HttpStatus.CONFLICT;

    public ConflictException() {
        super(CODE, DESCRIPTION, HTTPSTATUS);
    }

    public ConflictException(String message) {
        super(CODE, message, HTTPSTATUS);
    }
}
