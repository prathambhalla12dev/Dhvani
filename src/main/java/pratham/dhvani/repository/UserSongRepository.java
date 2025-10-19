package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pratham.dhvani.enums.UserPreference;
import pratham.dhvani.model.User_Song;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSongRepository extends JpaRepository<@NonNull User_Song, @NonNull Long> {
    List<User_Song> findByUser_idAndUserPreference(long userId, UserPreference preference);
    Optional<User_Song> findByUser_idAndSong_id(long userId, long songId);
    void deleteByUser_idAndSong_id(long userId, long songId);
}