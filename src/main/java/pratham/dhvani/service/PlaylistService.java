package pratham.dhvani.service;

import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.PlaylistRequestDto;

public interface PlaylistService {
    ApiResponseDto createPlaylist(PlaylistRequestDto requestDto, String username);
    ApiResponseDto getPlaylistById(long id);
    ApiResponseDto getUserPlaylists(String username);
    ApiResponseDto addSongToPlaylist(long playlistId, long songId, String username);
    ApiResponseDto removeSongFromPlaylist(long playlistId, long songId, String username);
    ApiResponseDto getPlaylistSongs(long playlistId);
    ApiResponseDto updatePlaylist(long id, PlaylistRequestDto requestDto, String username);
    ApiResponseDto deletePlaylist(long id, String username);
}