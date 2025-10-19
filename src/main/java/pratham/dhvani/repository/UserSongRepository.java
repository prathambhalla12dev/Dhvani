package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pratham.dhvani.enums.UserPreference;
import pratham.dhvani.model.User_Song;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSongRepository extends JpaRepository<@NonNull User_Song, @NonNull Long> {

    @Query("SELECT us FROM User_Song us WHERE us.user_id = :userId AND us.userPreference = :preference")
    List<User_Song> findByUserIdAndPreference(@Param("userId") long userId, @Param("preference") UserPreference preference);

    @Query("SELECT us FROM User_Song us WHERE us.user_id = :userId AND us.song_id = :songId")
    Optional<User_Song> findByUserIdAndSongId(@Param("userId") long userId, @Param("songId") long songId);

    @Modifying
    @Query("DELETE FROM User_Song us WHERE us.user_id = :userId AND us.song_id = :songId")
    void deleteByUserIdAndSongId(@Param("userId") long userId, @Param("songId") long songId);
}