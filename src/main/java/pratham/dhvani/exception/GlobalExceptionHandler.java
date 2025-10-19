package pratham.dhvani.exception;

import lombok.NonNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pratham.dhvani.dto.ApiResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Request body is missing or malformed. Please send valid JSON data.";

        String exMessage = ex.getMessage();
        if (exMessage != null) {
            if (exMessage.contains("pratham.dhvani.enums.Country")) {
                errorMessage = "Invalid country. Allowed values: INDIA, UNITED_STATES, UNITED_KINGDOM";
            } else if (exMessage.contains("pratham.dhvani.enums.Gender")) {
                errorMessage = "Invalid gender. Please provide a valid gender value.";
            }
        }

        ApiResponseDto response = new ApiResponseDto(errorMessage, "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        String firstErrorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid input data.");
        ApiResponseDto response = new ApiResponseDto(firstErrorMessage, "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ApiResponseDto response = new ApiResponseDto(ex.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponseDto response = new ApiResponseDto(ex.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}