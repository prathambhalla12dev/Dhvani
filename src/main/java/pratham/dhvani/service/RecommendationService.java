package pratham.dhvani.service;

import pratham.dhvani.dto.ApiResponseDto;

public interface RecommendationService {
    ApiResponseDto getRecommendationsForUser(String username);
    ApiResponseDto generateRecommendations(String username);
    ApiResponseDto clearRecommendations(String username);
}