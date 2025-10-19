package pratham.dhvani.util;

import org.springframework.stereotype.Component;
import pratham.dhvani.dto.ApiResponseDto;

/**
 * Builder utility for creating consistent API responses
 * Ensures ApiResponseDto is never null and follows consistent patterns
 */
@Component
public class ApiResponseBuilder {

    public static ApiResponseDto success(String message) {
        if (message == null || message.trim().isEmpty()) {
            message = "Operation completed successfully";
        }
        return new ApiResponseDto(message, "SUCCESS");
    }

    public static ApiResponseDto error(String message) {
        if (message == null || message.trim().isEmpty()) {
            message = "An error occurred";
        }
        return new ApiResponseDto(message, "ERROR");
    }

    public static ApiResponseDto validationError(String message) {
        if (message == null || message.trim().isEmpty()) {
            message = "Validation failed";
        }
        return new ApiResponseDto(message, "ERROR");
    }

    public static ApiResponseDto internalError(String message) {
        if (message == null || message.trim().isEmpty()) {
            message = "Internal server error occurred";
        }
        return new ApiResponseDto(message, "ERROR");
    }
}