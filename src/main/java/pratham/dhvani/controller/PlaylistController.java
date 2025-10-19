package pratham.dhvani.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.PlaylistRequestDto;
import pratham.dhvani.service.PlaylistService;

@Slf4j
@RestController
@RequestMapping("/dhvani/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> createPlaylist(
            @Valid @RequestBody @NonNull PlaylistRequestDto requestDto,
            Authentication authentication) {
        log.info("Creating playlist: {} for user: {}", requestDto.getName(), authentication.getName());
        ApiResponseDto response = playlistService.createPlaylist(requestDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getPlaylistById(@PathVariable long id) {
        log.info("Fetching playlist with ID: {}", id);
        ApiResponseDto response = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getUserPlaylists(Authentication authentication) {
        log.info("Fetching playlists for user: {}", authentication.getName());
        ApiResponseDto response = playlistService.getUserPlaylists(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{playlistId}/song/{songId}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> addSongToPlaylist(
            @PathVariable long playlistId,
            @PathVariable long songId,
            Authentication authentication) {
        log.info("Adding song {} to playlist {} by user: {}", songId, playlistId, authentication.getName());
        ApiResponseDto response = playlistService.addSongToPlaylist(playlistId, songId, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{playlistId}/song/{songId}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> removeSongFromPlaylist(
            @PathVariable long playlistId,
            @PathVariable long songId,
            Authentication authentication) {
        log.info("Removing song {} from playlist {} by user: {}", songId, playlistId, authentication.getName());
        ApiResponseDto response = playlistService.removeSongFromPlaylist(playlistId, songId, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{playlistId}/songs")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getPlaylistSongs(@PathVariable long playlistId) {
        log.info("Fetching songs for playlist ID: {}", playlistId);
        ApiResponseDto response = playlistService.getPlaylistSongs(playlistId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> updatePlaylist(
            @PathVariable long id,
            @Valid @RequestBody @NonNull PlaylistRequestDto requestDto,
            Authentication authentication) {
        log.info("Updating playlist with ID: {} by user: {}", id, authentication.getName());
        ApiResponseDto response = playlistService.updatePlaylist(id, requestDto, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> deletePlaylist(
            @PathVariable long id,
            Authentication authentication) {
        log.info("Deleting playlist with ID: {} by user: {}", id, authentication.getName());
        ApiResponseDto response = playlistService.deletePlaylist(id, authentication.getName());
        return ResponseEntity.ok(response);
    }
}