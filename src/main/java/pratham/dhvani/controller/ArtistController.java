package pratham.dhvani.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.ArtistRequestDto;
import pratham.dhvani.enums.Country;
import pratham.dhvani.service.ArtistService;

@Slf4j
@RestController
@RequestMapping("/dhvani/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> createArtist(
            @Valid @RequestBody @NonNull ArtistRequestDto requestDto) {
        log.info("Creating artist: {}", requestDto.getName());
        ApiResponseDto response = artistService.createArtist(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getArtistById(@PathVariable long id) {
        log.info("Fetching artist with ID: {}", id);
        ApiResponseDto response = artistService.getArtistById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getAllArtists() {
        log.info("Fetching all artists");
        ApiResponseDto response = artistService.getAllArtists();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/country/{country}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getArtistsByCountry(@PathVariable Country country) {
        log.info("Fetching artists for country: {}", country);
        ApiResponseDto response = artistService.getArtistsByCountry(country);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> searchArtistByName(@RequestParam String name) {
        log.info("Searching artist by name: {}", name);
        ApiResponseDto response = artistService.searchArtistByName(name);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> updateArtist(
            @PathVariable long id,
            @Valid @RequestBody @NonNull ArtistRequestDto requestDto) {
        log.info("Updating artist with ID: {}", id);
        ApiResponseDto response = artistService.updateArtist(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> deleteArtist(@PathVariable long id) {
        log.info("Deleting artist with ID: {}", id);
        ApiResponseDto response = artistService.deleteArtist(id);
        return ResponseEntity.ok(response);
    }
}