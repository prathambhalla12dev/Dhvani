package pratham.dhvani.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.SongRequestDto;
import pratham.dhvani.enums.Genre;
import pratham.dhvani.enums.Language;
import pratham.dhvani.service.SongService;

@Slf4j
@RestController
@RequestMapping("/dhvani/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> createSong(
            @Valid @RequestBody @NonNull SongRequestDto requestDto) {
        log.info("Creating song: {}", requestDto.getTitle());
        ApiResponseDto response = songService.createSong(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getSongById(@PathVariable long id) {
        log.info("Fetching song with ID: {}", id);
        ApiResponseDto response = songService.getSongById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getAllSongs() {
        log.info("Fetching all songs");
        ApiResponseDto response = songService.getAllSongs();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/artist/{artistId}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getSongsByArtist(@PathVariable long artistId) {
        log.info("Fetching songs for artist ID: {}", artistId);
        ApiResponseDto response = songService.getSongsByArtist(artistId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/genre/{genre}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getSongsByGenre(@PathVariable Genre genre) {
        log.info("Fetching songs for genre: {}", genre);
        ApiResponseDto response = songService.getSongsByGenre(genre);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/language/{language}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getSongsByLanguage(@PathVariable Language language) {
        log.info("Fetching songs for language: {}", language);
        ApiResponseDto response = songService.getSongsByLanguage(language);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/album/{albumId}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getSongsByAlbum(@PathVariable long albumId) {
        log.info("Fetching songs for album ID: {}", albumId);
        ApiResponseDto response = songService.getSongsByAlbum(albumId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> updateSong(
            @PathVariable long id,
            @Valid @RequestBody @NonNull SongRequestDto requestDto) {
        log.info("Updating song with ID: {}", id);
        ApiResponseDto response = songService.updateSong(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> deleteSong(@PathVariable long id) {
        log.info("Deleting song with ID: {}", id);
        ApiResponseDto response = songService.deleteSong(id);
        return ResponseEntity.ok(response);
    }
}