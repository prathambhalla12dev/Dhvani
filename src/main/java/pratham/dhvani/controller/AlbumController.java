package pratham.dhvani.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pratham.dhvani.dto.AlbumRequestDto;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.service.AlbumService;

@Slf4j
@RestController
@RequestMapping("/dhvani/album")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> createAlbum(
            @Valid @RequestBody @NonNull AlbumRequestDto requestDto) {
        log.info("Creating album: {}", requestDto.getTitle());
        ApiResponseDto response = albumService.createAlbum(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getAlbumById(@PathVariable long id) {
        log.info("Fetching album with ID: {}", id);
        ApiResponseDto response = albumService.getAlbumById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getAllAlbums() {
        log.info("Fetching all albums");
        ApiResponseDto response = albumService.getAllAlbums();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/artist/{artistId}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getAlbumsByArtist(@PathVariable long artistId) {
        log.info("Fetching albums for artist ID: {}", artistId);
        ApiResponseDto response = albumService.getAlbumsByArtist(artistId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> updateAlbum(
            @PathVariable long id,
            @Valid @RequestBody @NonNull AlbumRequestDto requestDto) {
        log.info("Updating album with ID: {}", id);
        ApiResponseDto response = albumService.updateAlbum(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> deleteAlbum(@PathVariable long id) {
        log.info("Deleting album with ID: {}", id);
        ApiResponseDto response = albumService.deleteAlbum(id);
        return ResponseEntity.ok(response);
    }
}