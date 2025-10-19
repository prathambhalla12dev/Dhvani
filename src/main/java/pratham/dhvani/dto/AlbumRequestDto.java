package pratham.dhvani.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumRequestDto {

    @NotBlank(message = "Album title is required")
    @Size(min = 1, max = 100, message = "Album title must be between 1 and 100 characters")
    private String title;

    @NotNull(message = "Artist ID is required")
    @Positive(message = "Artist ID must be a positive number")
    private long artistId;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Release date must be in YYYY-MM-DD format")
    private String releaseDate;
}
