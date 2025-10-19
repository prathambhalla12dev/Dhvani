package pratham.dhvani.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.service.RecommendationService;

@Slf4j
@RestController
@RequestMapping("/dhvani/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> getRecommendations(Authentication authentication) {
        log.info("Fetching recommendations for user: {}", authentication.getName());
        ApiResponseDto response = recommendationService.getRecommendationsForUser(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate")
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> generateRecommendations(Authentication authentication) {
        log.info("Generating recommendations for user: {}", authentication.getName());
        ApiResponseDto response = recommendationService.generateRecommendations(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public @NonNull ResponseEntity<@NonNull ApiResponseDto> clearRecommendations(Authentication authentication) {
        log.info("Clearing recommendations for user: {}", authentication.getName());
        ApiResponseDto response = recommendationService.clearRecommendations(authentication.getName());
        return ResponseEntity.ok(response);
    }
}