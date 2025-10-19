package pratham.dhvani.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pratham.dhvani.enums.Country;
import pratham.dhvani.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistRequestDto {

    @NotBlank(message = "Artist name is required")
    @Size(min = 1, max = 100, message = "Artist name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Country is required")
    private Country country;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in YYYY-MM-DD format")
    private String dateOfBirth;
}