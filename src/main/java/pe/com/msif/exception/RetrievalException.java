package pe.com.msif.exception;

import org.springframework.http.HttpStatus;

public class RetrievalException extends BusinessException {

    private static final String DESCRIPTION = "URI invalida para el userId.";
    private static final String CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    private static final HttpStatus HTTPSTATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public RetrievalException() {
        super(CODE, DESCRIPTION, HTTPSTATUS);
    }

    public RetrievalException(String message) {
        super(CODE, DESCRIPTION, HTTPSTATUS);
    }
}
