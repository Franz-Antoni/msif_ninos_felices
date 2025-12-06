package pe.com.msif.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import pe.com.msif.exception.enumerator.ExceptionEnum;
import pe.com.msif.exception.model.ApiExceptionModel;
import pe.com.msif.exception.response.ErrorResponse;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ BusinessException.class })
    public ResponseEntity<Object> handleBusinessException(final BusinessException ex,
                                                          final HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getCode(), ex.getMessage(), request);
    }

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<Object> handleBadRequestException(final BadRequestException ex,
                                                            final HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage(), request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUnauthorizedException(final UnauthorizedException ex,
                                                              final HttpServletRequest request) {
        return  buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getCode(), ex.getMessage(), request);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException ex,
                                                          final HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getCode(), ex.getMessage(), request);
    }

    @ExceptionHandler({ ConflictException.class })
    public ResponseEntity<Object> handleConflictException(final ConflictException ex,
                                                          final HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getCode(), ex.getMessage(), request);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex,
                                                                        final HttpServletRequest request) {

        var result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        var error = new ApiExceptionModel(HttpStatus.BAD_REQUEST, ex, request.getRequestURI());

        for (FieldError fieldError : fieldErrors) {
            error.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(new ErrorResponse<>(error), HttpStatus.valueOf(error.getStatus()));
    }

    @ExceptionHandler({ NoHandlerFoundException.class })
    public ResponseEntity<Object> handleNotFoundMethodException(final NoHandlerFoundException ex,
                                                                final HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_IMPLEMENTED, request);
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleIllegalException(final RuntimeException ex, final HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_IMPLEMENTED, request);
    }

    @ExceptionHandler({ org.springframework.dao.DuplicateKeyException.class,
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class })
    public ResponseEntity<Object> handleRuntimeException(final RuntimeException ex, final HttpServletRequest request) {
        var exception = ExceptionEnum.NOT_PARAMETER.getException();
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getCode(), exception.getMessage(),
                request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnknownHostException(final Exception ex, final HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({ RetrievalException.class })
    public ResponseEntity<Object> handleRetrievalException(final RetrievalException ex,
                                                           final HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getMessage(), request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus,
                                                      final HttpServletRequest request) {
        ApiExceptionModel errorResponse = new ApiExceptionModel(httpStatus, exception, request.getRequestURI());
        return new ResponseEntity<>(new ErrorResponse<>(errorResponse), httpStatus);
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String code, String message,
                                                      final HttpServletRequest request) {
        ApiExceptionModel errorResponse = new ApiExceptionModel(httpStatus, code, message, request.getRequestURI());
        return new ResponseEntity<>(new ErrorResponse<>(errorResponse), httpStatus);
    }
}
