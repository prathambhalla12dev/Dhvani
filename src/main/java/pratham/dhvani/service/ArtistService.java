package pratham.dhvani.service;

import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.ArtistRequestDto;
import pratham.dhvani.enums.Country;

public interface ArtistService {
    ApiResponseDto createArtist(ArtistRequestDto requestDto);
    ApiResponseDto getArtistById(long id);
    ApiResponseDto getAllArtists();
    ApiResponseDto getArtistsByCountry(Country country);
    ApiResponseDto searchArtistByName(String name);
    ApiResponseDto updateArtist(long id, ArtistRequestDto requestDto);
    ApiResponseDto deleteArtist(long id);
}