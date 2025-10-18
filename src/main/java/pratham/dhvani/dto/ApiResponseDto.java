package pratham.dhvani.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto {
    private String message;
    private String status;
}