package pe.com.msif.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiExceptionModel {

    private String timestamp;
    private int status;
    private String error;
    private String code;
    private String detail;
    private String path;
    private List<ValidationError> errors;

    private ApiExceptionModel() {
        var sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        this.timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));
    }

    public ApiExceptionModel(HttpStatus status, Throwable error, String path) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.detail = error.getLocalizedMessage();
        this.path = path;
    }

    public ApiExceptionModel(HttpStatus status, String code, String detail, String path) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.code = code;
        this.detail = detail;
        this.path = path;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message){
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, message));
    }
}
