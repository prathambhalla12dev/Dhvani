package pratham.dhvani.service;

import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.enums.UserPreference;

public interface UserSongService {
    ApiResponseDto setPreference(String username, long songId, UserPreference preference);
    ApiResponseDto getSongsByPreference(String username, UserPreference preference);
    ApiResponseDto getPreference(String username, long songId);
    ApiResponseDto removePreference(String username, long songId);
}