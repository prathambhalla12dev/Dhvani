package pratham.dhvani.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.enums.UserPreference;
import pratham.dhvani.model.Recommendation;
import pratham.dhvani.model.Song;
import pratham.dhvani.model.User;
import pratham.dhvani.model.User_Song;
import pratham.dhvani.repository.RecommendationRepository;
import pratham.dhvani.repository.SongRepository;
import pratham.dhvani.repository.UserRepository;
import pratham.dhvani.repository.UserSongRepository;
import pratham.dhvani.service.RecommendationService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final UserSongRepository userSongRepository;

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getRecommendationsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Recommendation> recommendations = recommendationRepository.findByUserId(user.getId());
        List<Song> songs = recommendations.stream()
                .map(rec -> songRepository.findById(rec.getSongId()).orElse(null))
                .filter(song -> song != null)
                .collect(Collectors.toList());

        return new ApiResponseDto("Recommendations retrieved successfully", "SUCCESS", songs);
    }

    @Override
    @Transactional
    public ApiResponseDto generateRecommendations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Clear existing recommendations
        recommendationRepository.deleteByUserId(user.getId());

        // Get user's liked and favorite songs
        List<User_Song> likedSongs = userSongRepository.findByUserIdAndPreference(user.getId(), UserPreference.LIKED);
        List<User_Song> favoriteSongs = userSongRepository.findByUserIdAndPreference(user.getId(), UserPreference.FAVOURITE);

        Set<Long> userSongIds = new HashSet<>();
        likedSongs.forEach(us -> userSongIds.add(us.getSong_id()));
        favoriteSongs.forEach(us -> userSongIds.add(us.getSong_id()));

        // If user has preferences, recommend based on them
        List<Song> recommendedSongs = new ArrayList<>();

        if (!userSongIds.isEmpty()) {
            // Get genres and languages from liked songs
            List<Song> userPreferredSongs = userSongIds.stream()
                    .map(id -> songRepository.findById(id).orElse(null))
                    .filter(song -> song != null)
                    .collect(Collectors.toList());

            Set<String> preferredGenres = userPreferredSongs.stream()
                    .map(s -> s.getGenre().toString())
                    .collect(Collectors.toSet());

            Set<String> preferredLanguages = userPreferredSongs.stream()
                    .map(s -> s.getLanguage().toString())
                    .collect(Collectors.toSet());

            // Find similar songs
            List<Song> allSongs = songRepository.findAll();
            recommendedSongs = allSongs.stream()
                    .filter(song -> !userSongIds.contains(song.getId()))
                    .filter(song -> preferredGenres.contains(song.getGenre().toString())
                            || preferredLanguages.contains(song.getLanguage().toString()))
                    .limit(10)
                    .collect(Collectors.toList());
        } else {
            // For new users, recommend popular/random songs
            List<Song> allSongs = songRepository.findAll();
            Collections.shuffle(allSongs);
            recommendedSongs = allSongs.stream().limit(10).collect(Collectors.toList());
        }

        // Save recommendations
        long now = System.currentTimeMillis();
        for (Song song : recommendedSongs) {
            Recommendation rec = new Recommendation();
            rec.setUserId(user.getId());
            rec.setSongId(song.getId());
            rec.setCreationTime(now);
            rec.setUpdationTime(now);
            recommendationRepository.save(rec);
        }

        return new ApiResponseDto("Recommendations generated successfully", "SUCCESS", recommendedSongs);
    }

    @Override
    @Transactional
    public ApiResponseDto clearRecommendations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        recommendationRepository.deleteByUserId(user.getId());
        return new ApiResponseDto("Recommendations cleared successfully", "SUCCESS");
    }
}