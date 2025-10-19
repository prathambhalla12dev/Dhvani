package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pratham.dhvani.model.Album;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<@NonNull Album, @NonNull Long> {
    List<Album> findByArtistId(long artistId);
    Optional<Album> findByTitle(String title);
    boolean existsByTitle(String title);
}