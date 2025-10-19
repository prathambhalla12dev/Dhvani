package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pratham.dhvani.model.Playlist;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<@NonNull Playlist, @NonNull Long> {
    List<Playlist> findByUserId(long userId);
    Optional<Playlist> findByNameAndUserId(String name, long userId);
    boolean existsByNameAndUserId(String name, long userId);
}