package pratham.dhvani.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.enums.UserPreference;
import pratham.dhvani.model.Song;
import pratham.dhvani.model.User;
import pratham.dhvani.model.User_Song;
import pratham.dhvani.repository.SongRepository;
import pratham.dhvani.repository.UserRepository;
import pratham.dhvani.repository.UserSongRepository;
import pratham.dhvani.service.UserSongService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSongServiceImpl implements UserSongService {

    private final UserSongRepository userSongRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Override
    @Transactional
    public ApiResponseDto setPreference(String username, long songId, UserPreference preference) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!songRepository.existsById(songId)) {
            throw new IllegalArgumentException("Song not found");
        }

        Optional<User_Song> existing = userSongRepository.findByUserIdAndSongId(user.getId(), songId);

        User_Song userSong;
        if (existing.isPresent()) {
            userSong = existing.get();
            userSong.setUserPreference(preference);
            userSong.setUpdationTime(System.currentTimeMillis());
        } else {
            userSong = new User_Song();
            userSong.setUser_id(user.getId());
            userSong.setSong_id(songId);
            userSong.setUserPreference(preference);
            long now = System.currentTimeMillis();
            userSong.setCreationTime(now);
            userSong.setUpdationTime(now);
        }

        userSongRepository.save(userSong);
        return new ApiResponseDto("Preference set successfully", "SUCCESS");
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getSongsByPreference(String username, UserPreference preference) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<User_Song> userSongs = userSongRepository.findByUserIdAndPreference(user.getId(), preference);
        List<Song> songs = userSongs.stream()
                .map(us -> songRepository.findById(us.getSong_id()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new ApiResponseDto("Songs retrieved successfully", "SUCCESS", songs);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getPreference(String username, long songId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Optional<User_Song> userSong = userSongRepository.findByUserIdAndSongId(user.getId(), songId);

        return userSong.map(user_song -> new ApiResponseDto("Preference retrieved successfully", "SUCCESS", user_song.getUserPreference())).orElseGet(() -> new ApiResponseDto("No preference set", "SUCCESS", UserPreference.NEUTRAL));
    }

    @Override
    @Transactional
    public ApiResponseDto removePreference(String username, long songId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userSongRepository.deleteByUserIdAndSongId(user.getId(), songId);
        return new ApiResponseDto("Preference removed successfully", "SUCCESS");
    }
}