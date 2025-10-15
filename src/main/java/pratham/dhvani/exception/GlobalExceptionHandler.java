package pratham.dhvani.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pratham.dhvani.dto.ApiResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseDto handleValidationException(ValidationException ex) {
        ApiResponseDto response = new ApiResponseDto();
        response.setMessage(ex.getMessage());
        response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return response;
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponseDto handleConflictException(ConflictException ex) {
        ApiResponseDto response = new ApiResponseDto();
        response.setMessage(ex.getMessage());
        response.setStatus(String.valueOf(HttpStatus.CONFLICT.value()));
        return response;
    }
}