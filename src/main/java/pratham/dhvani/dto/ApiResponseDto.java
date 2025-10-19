package pratham.dhvani.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto {
    private String message;
    private String status;
    private Object data;

    public ApiResponseDto(String message, String status) {
        this.message = message;
        this.status = status;
        this.data = null;
    }
}