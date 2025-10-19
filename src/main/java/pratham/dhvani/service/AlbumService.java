package pratham.dhvani.service;

import pratham.dhvani.dto.AlbumRequestDto;
import pratham.dhvani.dto.ApiResponseDto;

public interface AlbumService {
    ApiResponseDto createAlbum(AlbumRequestDto requestDto);
    ApiResponseDto getAlbumById(long id);
    ApiResponseDto getAllAlbums();
    ApiResponseDto getAlbumsByArtist(long artistId);
    ApiResponseDto updateAlbum(long id, AlbumRequestDto requestDto);
    ApiResponseDto deleteAlbum(long id);
}