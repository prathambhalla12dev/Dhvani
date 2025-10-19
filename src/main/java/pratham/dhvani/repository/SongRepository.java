package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pratham.dhvani.enums.Genre;
import pratham.dhvani.enums.Language;
import pratham.dhvani.model.Song;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<@NonNull Song, @NonNull Long> {
    List<Song> findByArtistId(long artistId);
    List<Song> findByGenre(Genre genre);
    List<Song> findByLanguage(Language language);
    List<Song> findByAlbumId(long albumId);
    Optional<Song> findByTitle(String title);
    boolean existsByTitle(String title);
}