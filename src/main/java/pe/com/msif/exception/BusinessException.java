package pe.com.msif.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pe.com.msif.exception.model.ExceptionModel;

@Getter
@Setter
public class BusinessException extends ResponseStatusException {

    private final String code;

    public BusinessException(String code, String message, HttpStatus httpStatus) {
        super(httpStatus, message);
        this.code = code;
    }

    public BusinessException(ExceptionModel exception, HttpStatus httpStatus) {
        super(httpStatus, exception.getMessage());
        this.code = exception.getCode();
    }
}
