package pe.com.msif.exception.enumerator;

import lombok.Getter;
import pe.com.msif.exception.model.ExceptionModel;

@Getter
public enum ExceptionEnum {
    NOT_PARAMETER("Err-001", "Required request body is missing.");

    private final ExceptionModel exception;

    ExceptionEnum(String code, String message) {
        this.exception = new ExceptionModel(code, message);
    }
}
