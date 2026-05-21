package com.example.youtube.playlist.controller;

import com.example.youtube.common.context.RequestContextHolder;
import com.example.youtube.common.response.ApiResponse;
import com.example.youtube.playlist.dto.request.AddPlaylistItemRequest;
import com.example.youtube.playlist.dto.request.CreatePlaylistRequest;
import com.example.youtube.playlist.dto.request.ReorderPlaylistRequest;
import com.example.youtube.playlist.dto.request.UpdatePlaylistRequest;
import com.example.youtube.playlist.dto.response.PlaylistResponse;
import com.example.youtube.playlist.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping("/playlists")
    public ResponseEntity<ApiResponse<PlaylistResponse>> create(@RequestBody @Valid CreatePlaylistRequest request) {
        Long userId = RequestContextHolder.requireUserId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(playlistService.createUserPlaylist(request, userId)));
    }

    @GetMapping("/playlists/{playlistId}")
    public ApiResponse<PlaylistResponse> getById(@PathVariable Long playlistId) {
        Long userId = RequestContextHolder.get() == null ? null : RequestContextHolder.get().getUserId();
        return ApiResponse.ok(playlistService.getPlaylist(playlistId, userId));
    }

    @GetMapping("/playlists/me")
    public ApiResponse<List<PlaylistResponse>> myPlaylists() {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.getMyPlaylists(userId));
    }

    @PutMapping("/playlists/{playlistId}")
    public ApiResponse<PlaylistResponse> update(@PathVariable Long playlistId,
                                                @RequestBody UpdatePlaylistRequest request) {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.updateUserPlaylist(playlistId, request, userId));
    }

    @DeleteMapping("/playlists/{playlistId}")
    public ApiResponse<Void> delete(@PathVariable Long playlistId) {
        Long userId = RequestContextHolder.requireUserId();
        playlistService.deleteUserPlaylist(playlistId, userId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/playlists/{playlistId}/items")
    public ApiResponse<PlaylistResponse> addItem(@PathVariable Long playlistId,
                                                 @RequestBody @Valid AddPlaylistItemRequest request) {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.addVideoToPlaylist(playlistId, request.getVideoId(), userId, "USER"));
    }

    @DeleteMapping("/playlists/{playlistId}/items/{videoId}")
    public ApiResponse<PlaylistResponse> removeItem(@PathVariable Long playlistId, @PathVariable Long videoId) {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.removeVideoFromPlaylist(playlistId, videoId, userId));
    }

    @PatchMapping("/playlists/{playlistId}/items/reorder")
    public ApiResponse<PlaylistResponse> reorder(@PathVariable Long playlistId,
                                                 @RequestBody ReorderPlaylistRequest request) {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.reorderPlaylistItems(playlistId, request, userId));
    }

    @GetMapping("/system-playlists")
    public ApiResponse<List<PlaylistResponse>> systemPlaylists() {
        return ApiResponse.ok(playlistService.getSystemPlaylists());
    }

    @GetMapping("/system-playlists/{code}")
    public ApiResponse<PlaylistResponse> systemPlaylistByCode(@PathVariable String code) {
        return ApiResponse.ok(playlistService.getSystemPlaylistByCode(code));
    }

    @GetMapping("/watch-later")
    public ApiResponse<PlaylistResponse> watchLater() {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.getWatchLater(userId));
    }

    @PostMapping("/watch-later/{videoId}")
    public ApiResponse<PlaylistResponse> addWatchLater(@PathVariable Long videoId) {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.addWatchLater(userId, videoId));
    }

    @DeleteMapping("/watch-later/{videoId}")
    public ApiResponse<PlaylistResponse> removeWatchLater(@PathVariable Long videoId) {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.removeWatchLater(userId, videoId));
    }

    @GetMapping("/history")
    public ApiResponse<PlaylistResponse> history() {
        Long userId = RequestContextHolder.requireUserId();
        return ApiResponse.ok(playlistService.getHistory(userId));
    }

    @DeleteMapping("/history")
    public ApiResponse<Void> clearHistory() {
        Long userId = RequestContextHolder.requireUserId();
        playlistService.clearHistory(userId);
        return ApiResponse.ok(null);
    }
}
