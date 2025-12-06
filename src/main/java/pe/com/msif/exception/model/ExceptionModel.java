package pe.com.msif.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionModel extends Throwable {

    private final String code;
    private final String message;

    public ExceptionModel(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
