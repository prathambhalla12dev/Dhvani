package pratham.dhvani.exception;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pratham.dhvani.dto.ApiResponseDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {
        String errorMessage = "Request body is missing or malformed. Please send valid JSON data.";

        String exMessage = ex.getMessage();
        if (exMessage != null) {
            if (exMessage.contains("pratham.dhvani.enums.Country")) {
                errorMessage = "invalid country";
            } else if (exMessage.contains("pratham.dhvani.enums.Gender")) {
                errorMessage = "invalid gender";
            } else if (exMessage.contains("pratham.dhvani.enums.Genre")) {
                errorMessage = "invalid genre";
            } else if (exMessage.contains("pratham.dhvani.enums.Energy")) {
                errorMessage = "invalid energy level";
            } else if (exMessage.contains("pratham.dhvani.enums.Mood")) {
                errorMessage = "invalid mood";
            } else if (exMessage.contains("pratham.dhvani.enums.Language")) {
                errorMessage = "invalid language";
            }
        }

        log.error("HTTP message not readable: {}", errorMessage);
        ApiResponseDto response = new ApiResponseDto(errorMessage, "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleValidationException(
            MethodArgumentNotValidException ex) {
        String firstErrorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid input data.");

        log.error("Validation error: {}", firstErrorMessage);
        ApiResponseDto response = new ApiResponseDto(firstErrorMessage, "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleUserAlreadyExists(
            UserAlreadyExistsException ex) {
        log.error("User already exists: {}", ex.getMessage());
        ApiResponseDto response = new ApiResponseDto(ex.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleIllegalArgument(
            IllegalArgumentException ex) {
        log.error("Illegal argument: {}", ex.getMessage());
        ApiResponseDto response = new ApiResponseDto(ex.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ApiResponseDto response = new ApiResponseDto(
                "An unexpected error occurred. Please try again later.",
                "ERROR"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}