package pe.com.msif.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BusinessException {

    private static final String DESCRIPTION = "Ocurrip un error inesperado.";
    private static final String CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private static final HttpStatus HTTPSTATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
      super(CODE, DESCRIPTION + " " + message, HTTPSTATUS);
    }
}
