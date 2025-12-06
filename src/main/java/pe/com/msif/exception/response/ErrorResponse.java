package pe.com.msif.exception.response;

import lombok.Getter;

@Getter
public class ErrorResponse<T> {
    private T error;

    public ErrorResponse(T error) {
        this.error = error;
    }
}
