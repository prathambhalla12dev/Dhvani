package pratham.dhvani.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.ArtistRequestDto;
import pratham.dhvani.enums.Country;
import pratham.dhvani.model.Artist;
import pratham.dhvani.repository.ArtistRepository;
import pratham.dhvani.service.ArtistService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Override
    @Transactional
    public ApiResponseDto createArtist(ArtistRequestDto dto) {
        if (artistRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Artist with this name already exists");
        }

        Artist artist = new Artist();
        artist.setName(dto.getName());
        artist.setCountry(dto.getCountry());
        artist.setGender(dto.getGender());
        artist.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        long now = System.currentTimeMillis();
        artist.setCreationTime(now);
        artist.setUpdationTime(now);

        Artist saved = artistRepository.save(artist);
        return new ApiResponseDto("Artist created successfully", "SUCCESS", saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getArtistById(long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        return new ApiResponseDto("Artist retrieved successfully", "SUCCESS", artist);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        return new ApiResponseDto("Artists retrieved successfully", "SUCCESS", artists);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getArtistsByCountry(Country country) {
        List<Artist> artists = artistRepository.findByCountry(country);
        return new ApiResponseDto("Artists retrieved successfully", "SUCCESS", artists);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto searchArtistByName(String name) {
        List<Artist> artists = artistRepository.findByNameContainingIgnoreCase(name);
        return new ApiResponseDto("Artists retrieved successfully", "SUCCESS", artists);
    }

    @Override
    @Transactional
    public ApiResponseDto updateArtist(long id, ArtistRequestDto dto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));

        artist.setName(dto.getName());
        artist.setCountry(dto.getCountry());
        artist.setGender(dto.getGender());
        artist.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        artist.setUpdationTime(System.currentTimeMillis());

        Artist updated = artistRepository.save(artist);
        return new ApiResponseDto("Artist updated successfully", "SUCCESS", updated);
    }

    @Override
    @Transactional
    public ApiResponseDto deleteArtist(long id) {
        if (!artistRepository.existsById(id)) {
            throw new IllegalArgumentException("Artist not found");
        }
        artistRepository.deleteById(id);
        return new ApiResponseDto("Artist deleted successfully", "SUCCESS");
    }
}