package pratham.dhvani.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import pratham.dhvani.enums.Country;
import pratham.dhvani.enums.Gender;

/**
 * Fixed validation annotations - removed @NotBlank from enum fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "username is required")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "invalid phone number format")
    private String phoneNumber;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

    @NotBlank(message = "confirm your password")
    private String confirmPassword;

    @NotNull(message = "country is required")
    private Country country;

    @NotBlank(message = "date of birth is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "date must be in YYYY-MM-DD format")
    private String dateOfBirth;

    @NotNull(message = "gender is required")
    private Gender gender;

    @NotBlank(message = "full name is required")
    @Size(min = 1, max = 100, message = "full name must be between 1 and 100 characters")
    private String fullName;
}