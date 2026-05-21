package com.example.youtube.playlist.service;

import com.example.youtube.playlist.dto.request.CreatePlaylistRequest;
import com.example.youtube.playlist.dto.request.ReorderPlaylistRequest;
import com.example.youtube.playlist.dto.request.UpdatePlaylistRequest;
import com.example.youtube.playlist.dto.response.PlaylistResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PlaylistService {
    PlaylistResponse createUserPlaylist(CreatePlaylistRequest request, Long userId);

    PlaylistResponse getPlaylist(Long playlistId, Long currentUserId);

    List<PlaylistResponse> getMyPlaylists(Long userId);

    PlaylistResponse updateUserPlaylist(Long playlistId, UpdatePlaylistRequest request, Long userId);

    void deleteUserPlaylist(Long playlistId, Long userId);

    PlaylistResponse addVideoToPlaylist(Long playlistId, Long videoId, Long userId, String source);

    PlaylistResponse removeVideoFromPlaylist(Long playlistId, Long videoId, Long userId);

    PlaylistResponse reorderPlaylistItems(Long playlistId, ReorderPlaylistRequest request, Long userId);

    PlaylistResponse getWatchLater(Long userId);

    PlaylistResponse addWatchLater(Long userId, Long videoId);

    PlaylistResponse removeWatchLater(Long userId, Long videoId);

    PlaylistResponse getHistory(Long userId);

    void clearHistory(Long userId);

    List<PlaylistResponse> getSystemPlaylists();

    PlaylistResponse getSystemPlaylistByCode(String code);

    void ensureDefaultUserPlaylists(Long userId);

    void addHistoryItemInternal(Long userId, Long videoId, LocalDateTime watchedAt);

    void updateSystemPlaylistInternal(String playlistCode, List<Long> videoIds);
}
