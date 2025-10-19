package pratham.dhvani.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pratham.dhvani.enums.Energy;
import pratham.dhvani.enums.Genre;
import pratham.dhvani.enums.Language;
import pratham.dhvani.enums.Mood;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongRequestDto {

    @NotBlank(message = "Song title is required")
    @Size(min = 1, max = 100, message = "Song title must be between 1 and 100 characters")
    private String title;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @NotNull(message = "Energy level is required")
    private Energy energy;

    @NotNull(message = "Mood is required")
    private Mood mood;

    @NotNull(message = "Artist ID is required")
    @Positive(message = "Artist ID must be a positive number")
    private long artistId;

    @Positive(message = "Album ID must be a positive number")
    private long albumId;

    @NotNull(message = "Language is required")
    private Language language;

    @DecimalMin(value = "0.0", inclusive = false, message = "Tempo must be greater than 0")
    private double tempo;
}