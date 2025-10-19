package pratham.dhvani.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pratham.dhvani.model.Playlist_Song;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistSongRepository extends JpaRepository<@NonNull Playlist_Song, @NonNull Long> {
    List<Playlist_Song> findByPlaylistId(long playlistId);
    Optional<Playlist_Song> findByPlaylistIdAndSongId(long playlistId, long songId);
    void deleteByPlaylistIdAndSongId(long playlistId, long songId);
    boolean existsByPlaylistIdAndSongId(long playlistId, long songId);
}