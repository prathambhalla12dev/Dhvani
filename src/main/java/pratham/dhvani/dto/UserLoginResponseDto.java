package pratham.dhvani.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDto {
    private String token;
    private String tokenType;
    private long expiresIn;
    private String username;
}