package pratham.dhvani.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.SongRequestDto;
import pratham.dhvani.enums.Genre;
import pratham.dhvani.enums.Language;
import pratham.dhvani.model.Song;
import pratham.dhvani.repository.AlbumRepository;
import pratham.dhvani.repository.ArtistRepository;
import pratham.dhvani.repository.SongRepository;
import pratham.dhvani.service.SongService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Override
    @Transactional
    public ApiResponseDto createSong(SongRequestDto dto) {
        if (songRepository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("Song with this title already exists");
        }

        if (!artistRepository.existsById(dto.getArtistId())) {
            throw new IllegalArgumentException("Artist not found");
        }

        if (dto.getAlbumId() > 0 && !albumRepository.existsById(dto.getAlbumId())) {
            throw new IllegalArgumentException("Album not found");
        }

        Song song = new Song();
        song.setTitle(dto.getTitle());
        song.setGenre(dto.getGenre());
        song.setEnergy(dto.getEnergy());
        song.setMood(dto.getMood());
        song.setArtistId(dto.getArtistId());
        song.setAlbumId(dto.getAlbumId());
        song.setLanguage(dto.getLanguage());
        song.setTempo(dto.getTempo());
        long now = System.currentTimeMillis();
        song.setCreationTime(now);
        song.setUpdationTime(now);

        Song saved = songRepository.save(song);
        return new ApiResponseDto("Song created successfully", "SUCCESS", saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getSongById(long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));
        return new ApiResponseDto("Song retrieved successfully", "SUCCESS", song);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return new ApiResponseDto("Songs retrieved successfully", "SUCCESS", songs);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getSongsByArtist(long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new IllegalArgumentException("Artist not found");
        }
        List<Song> songs = songRepository.findByArtistId(artistId);
        return new ApiResponseDto("Songs retrieved successfully", "SUCCESS", songs);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getSongsByGenre(Genre genre) {
        List<Song> songs = songRepository.findByGenre(genre);
        return new ApiResponseDto("Songs retrieved successfully", "SUCCESS", songs);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getSongsByLanguage(Language language) {
        List<Song> songs = songRepository.findByLanguage(language);
        return new ApiResponseDto("Songs retrieved successfully", "SUCCESS", songs);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getSongsByAlbum(long albumId) {
        if (!albumRepository.existsById(albumId)) {
            throw new IllegalArgumentException("Album not found");
        }
        List<Song> songs = songRepository.findByAlbumId(albumId);
        return new ApiResponseDto("Songs retrieved successfully", "SUCCESS", songs);
    }

    @Override
    @Transactional
    public ApiResponseDto updateSong(long id, SongRequestDto dto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        if (!artistRepository.existsById(dto.getArtistId())) {
            throw new IllegalArgumentException("Artist not found");
        }

        if (dto.getAlbumId() > 0 && !albumRepository.existsById(dto.getAlbumId())) {
            throw new IllegalArgumentException("Album not found");
        }

        song.setTitle(dto.getTitle());
        song.setGenre(dto.getGenre());
        song.setEnergy(dto.getEnergy());
        song.setMood(dto.getMood());
        song.setArtistId(dto.getArtistId());
        song.setAlbumId(dto.getAlbumId());
        song.setLanguage(dto.getLanguage());
        song.setTempo(dto.getTempo());
        song.setUpdationTime(System.currentTimeMillis());

        Song updated = songRepository.save(song);
        return new ApiResponseDto("Song updated successfully", "SUCCESS", updated);
    }

    @Override
    @Transactional
    public ApiResponseDto deleteSong(long id) {
        if (!songRepository.existsById(id)) {
            throw new IllegalArgumentException("Song not found");
        }
        songRepository.deleteById(id);
        return new ApiResponseDto("Song deleted successfully", "SUCCESS");
    }
}