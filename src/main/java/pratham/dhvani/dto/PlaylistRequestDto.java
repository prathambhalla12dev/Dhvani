package pratham.dhvani.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistRequestDto {

    @NotBlank(message = "Playlist name is required")
    @Size(min = 1, max = 100, message = "Playlist name must be between 1 and 100 characters")
    private String name;
}
