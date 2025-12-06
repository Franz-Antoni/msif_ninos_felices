package pe.com.msif.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {

    private static final String DESCRIPTION = "No se encontro el registro.";
    private static final String CODE = String.valueOf(HttpStatus.NOT_FOUND.value());
    private static final HttpStatus HTTPSTATUS = HttpStatus.NOT_FOUND;

    public NotFoundException() {
        super(CODE, DESCRIPTION, HTTPSTATUS);
    }

    public NotFoundException(String message) {
        super(CODE, DESCRIPTION + " " + message, HTTPSTATUS);
    }
}
