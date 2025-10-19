package pratham.dhvani.service;

import pratham.dhvani.dto.AlbumRequestDto;
import pratham.dhvani.dto.ApiResponseDto;

import java.util.List;

public interface AlbumService {
    ApiResponseDto createAlbum(AlbumRequestDto requestDto);
    ApiResponseDto getAlbumById(long id);
    List<ApiResponseDto> getAllAlbums();
    List<ApiResponseDto> getAlbumsByArtist(long artistId);
    ApiResponseDto updateAlbum(long id, AlbumRequestDto requestDto);
    void deleteAlbum(long id);
}