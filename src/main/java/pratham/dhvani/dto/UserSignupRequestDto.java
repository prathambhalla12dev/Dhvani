package pratham.dhvani.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import pratham.dhvani.enums.Country;
import pratham.dhvani.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "username is required")
    @NotNull
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "phone number is required")
    @NotNull
    private String phoneNumber;

    @NotBlank(message = "password is required")
    @NotNull
    @Size(min = 8, message = "password must be of at least 8 characters")
    private String password;

    @NotBlank(message = "confirm your password")
    @NotNull
    private String confirmPassword;

    @NotBlank(message = "country is required")
    @NotNull
    private Country country;

    @NotBlank(message = "please add the Date of Birth")
    @NotNull
    private String dateOfBirth;

    @NotBlank(message = "please enter your gender")
    @NotNull
    private Gender gender;
}