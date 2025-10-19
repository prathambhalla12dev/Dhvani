package pratham.dhvani.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratham.dhvani.dto.ApiResponseDto;
import pratham.dhvani.dto.PlaylistRequestDto;
import pratham.dhvani.model.Playlist;
import pratham.dhvani.model.Playlist_Song;
import pratham.dhvani.model.Song;
import pratham.dhvani.model.User;
import pratham.dhvani.repository.PlaylistRepository;
import pratham.dhvani.repository.PlaylistSongRepository;
import pratham.dhvani.repository.SongRepository;
import pratham.dhvani.repository.UserRepository;
import pratham.dhvani.service.PlaylistService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Override
    @Transactional
    public ApiResponseDto createPlaylist(PlaylistRequestDto dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (playlistRepository.existsByNameAndUserId(dto.getName(), user.getId())) {
            throw new IllegalArgumentException("Playlist with this name already exists");
        }

        Playlist playlist = new Playlist();
        playlist.setName(dto.getName());
        playlist.setUserId(user.getId());
        long now = System.currentTimeMillis();
        playlist.setCreationTime(now);
        playlist.setUpdationTime(now);

        Playlist saved = playlistRepository.save(playlist);
        return new ApiResponseDto("Playlist created successfully", "SUCCESS", saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getPlaylistById(long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
        return new ApiResponseDto("Playlist retrieved successfully", "SUCCESS", playlist);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getUserPlaylists(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Playlist> playlists = playlistRepository.findByUserId(user.getId());
        return new ApiResponseDto("Playlists retrieved successfully", "SUCCESS", playlists);
    }

    @Override
    @Transactional
    public ApiResponseDto addSongToPlaylist(long playlistId, long songId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        if (playlist.getUserId() != user.getId()) {
            throw new IllegalArgumentException("You don't have permission to modify this playlist");
        }

        if (!songRepository.existsById(songId)) {
            throw new IllegalArgumentException("Song not found");
        }

        if (playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId)) {
            throw new IllegalArgumentException("Song already exists in playlist");
        }

        Playlist_Song ps = new Playlist_Song();
        ps.setPlaylistId(playlistId);
        ps.setSongId(songId);
        long now = System.currentTimeMillis();
        ps.setCreationTime(now);
        ps.setUpdationTime(now);

        playlistSongRepository.save(ps);
        return new ApiResponseDto("Song added to playlist successfully", "SUCCESS");
    }

    @Override
    @Transactional
    public ApiResponseDto removeSongFromPlaylist(long playlistId, long songId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        if (playlist.getUserId() != user.getId()) {
            throw new IllegalArgumentException("You don't have permission to modify this playlist");
        }

        playlistSongRepository.deleteByPlaylistIdAndSongId(playlistId, songId);
        return new ApiResponseDto("Song removed from playlist successfully", "SUCCESS");
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDto getPlaylistSongs(long playlistId) {
        if (!playlistRepository.existsById(playlistId)) {
            throw new IllegalArgumentException("Playlist not found");
        }

        List<Playlist_Song> playlistSongs = playlistSongRepository.findByPlaylistId(playlistId);
        List<Song> songs = playlistSongs.stream()
                .map(ps -> songRepository.findById(ps.getSongId()).orElse(null))
                .filter(song -> song != null)
                .collect(Collectors.toList());

        return new ApiResponseDto("Playlist songs retrieved successfully", "SUCCESS", songs);
    }

    @Override
    @Transactional
    public ApiResponseDto updatePlaylist(long id, PlaylistRequestDto dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        if (playlist.getUserId() != user.getId()) {
            throw new IllegalArgumentException("You don't have permission to modify this playlist");
        }

        playlist.setName(dto.getName());
        playlist.setUpdationTime(System.currentTimeMillis());

        Playlist updated = playlistRepository.save(playlist);
        return new ApiResponseDto("Playlist updated successfully", "SUCCESS", updated);
    }

    @Override
    @Transactional
    public ApiResponseDto deletePlaylist(long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        if (playlist.getUserId() != user.getId()) {
            throw new IllegalArgumentException("You don't have permission to delete this playlist");
        }

        playlistRepository.deleteById(id);
        return new ApiResponseDto("Playlist deleted successfully", "SUCCESS");
    }
}