package pratham.dhvani.exception;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pratham.dhvani.dto.ApiResponseDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleUserAlreadyExists(
            @NonNull UserAlreadyExistsException ex) {
        log.error("User already exists: {}", ex.getMessage());
        ApiResponseDto response = new ApiResponseDto(ex.getMessage(), "ERROR");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleIllegalArgument(
            @NonNull IllegalArgumentException ex) {
        log.error("Illegal argument: {}", ex.getMessage());
        ApiResponseDto response = new ApiResponseDto(ex.getMessage(), "ERROR");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleValidationErrors(
            @NonNull MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validation error");
        log.error("Validation error: {}", errors);
        ApiResponseDto response = new ApiResponseDto(errors, "ERROR");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> handleGeneralException(
            @NonNull Exception ex) {
        // Log the actual error details for debugging
        log.error("Internal server error occurred", ex);

        // Return generic message to user but with actual error class name for debugging
        String errorMessage = "Internal server error: " + ex.getClass().getSimpleName();
        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            errorMessage += " - " + ex.getMessage();
        }

        ApiResponseDto response = new ApiResponseDto(errorMessage, "ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}