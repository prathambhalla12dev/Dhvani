package pratham.dhvani.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.enums.UserPreference;
import pratham.dhvani.service.UserSongService;

@Slf4j
@RestController
@RequestMapping("/dhvani/user-song")
@RequiredArgsConstructor
public class UserSongController {

    private final UserSongService userSongService;

    @PostMapping("/{songId}/preference")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> setPreference(
            @PathVariable long songId,
            @RequestParam UserPreference preference,
            Authentication authentication) {
        log.info("Setting preference {} for song {} by user: {}", preference, songId, authentication.getName());
        ApiResponseDto response = userSongService.setPreference(authentication.getName(), songId, preference);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/liked")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getLikedSongs(Authentication authentication) {
        log.info("Fetching liked songs for user: {}", authentication.getName());
        ApiResponseDto response = userSongService.getSongsByPreference(authentication.getName(), UserPreference.LIKED);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favourite")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getFavouriteSongs(Authentication authentication) {
        log.info("Fetching favourite songs for user: {}", authentication.getName());
        ApiResponseDto response = userSongService.getSongsByPreference(authentication.getName(), UserPreference.FAVOURITE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/disliked")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getDislikedSongs(Authentication authentication) {
        log.info("Fetching disliked songs for user: {}", authentication.getName());
        ApiResponseDto response = userSongService.getSongsByPreference(authentication.getName(), UserPreference.DISLIKED);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{songId}/preference")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getPreference(
            @PathVariable long songId,
            Authentication authentication) {
        log.info("Fetching preference for song {} by user: {}", songId, authentication.getName());
        ApiResponseDto response = userSongService.getPreference(authentication.getName(), songId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{songId}/preference")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> removePreference(
            @PathVariable long songId,
            Authentication authentication) {
        log.info("Removing preference for song {} by user: {}", songId, authentication.getName());
        ApiResponseDto response = userSongService.removePreference(authentication.getName(), songId);
        return ResponseEntity.ok(response);
    }
}