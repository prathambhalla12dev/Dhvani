package pratham.dhvani.service;

import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.SongRequestDto;
import pratham.dhvani.enums.Genre;
import pratham.dhvani.enums.Language;

public interface SongService {
    ApiResponseDto createSong(SongRequestDto requestDto);
    ApiResponseDto getSongById(long id);
    ApiResponseDto getAllSongs();
    ApiResponseDto getSongsByArtist(long artistId);
    ApiResponseDto getSongsByGenre(Genre genre);
    ApiResponseDto getSongsByLanguage(Language language);
    ApiResponseDto getSongsByAlbum(long albumId);
    ApiResponseDto updateSong(long id, SongRequestDto requestDto);
    ApiResponseDto deleteSong(long id);
}