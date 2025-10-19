package pratham.dhvani.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratham.dhvani.dto.AlbumRequestDto;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.model.Album;
import pratham.dhvani.repository.AlbumRepository;
import pratham.dhvani.repository.ArtistRepository;
import pratham.dhvani.service.AlbumService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @Override
    @Transactional
    public ApiResponseDto createAlbum(AlbumRequestDto dto) {
        if (albumRepository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("Album with this title already exists");
        }

        if (!artistRepository.existsById(dto.getArtistId())) {
            throw new IllegalArgumentException("Artist not found");
        }

        Album album = new Album();
        album.setTitle(dto.getTitle());
        album.setArtistId(dto.getArtistId());
        album.setReleaseDate(LocalDate.parse(dto.getReleaseDate()));
        long now = System.currentTimeMillis();
        album.setCreationTime(now);
        album.setUpdationTime(now);

        Album saved = albumRepository.save(album);
        return new ApiResponseDto("Album created successfully", "SUCCESS", saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getAlbumById(long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));
        return new ApiResponseDto("Album retrieved successfully", "SUCCESS", album);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return new ApiResponseDto("Albums retrieved successfully", "SUCCESS", albums);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getAlbumsByArtist(long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new IllegalArgumentException("Artist not found");
        }
        List<Album> albums = albumRepository.findByArtistId(artistId);
        return new ApiResponseDto("Albums retrieved successfully", "SUCCESS", albums);
    }

    @Override
    @Transactional
    public ApiResponseDto updateAlbum(long id, AlbumRequestDto dto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));

        if (!artistRepository.existsById(dto.getArtistId())) {
            throw new IllegalArgumentException("Artist not found");
        }

        album.setTitle(dto.getTitle());
        album.setArtistId(dto.getArtistId());
        album.setReleaseDate(LocalDate.parse(dto.getReleaseDate()));
        album.setUpdationTime(System.currentTimeMillis());

        Album updated = albumRepository.save(album);
        return new ApiResponseDto("Album updated successfully", "SUCCESS", updated);
    }

    @Override
    @Transactional
    public ApiResponseDto deleteAlbum(long id) {
        if (!albumRepository.existsById(id)) {
            throw new IllegalArgumentException("Album not found");
        }
        albumRepository.deleteById(id);
        return new ApiResponseDto("Album deleted successfully", "SUCCESS");
    }
}