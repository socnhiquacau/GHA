package com.example.youtube.playlist.service.impl;

import com.example.youtube.common.exception.BadRequestException;
import com.example.youtube.common.exception.ConflictException;
import com.example.youtube.common.exception.ForbiddenException;
import com.example.youtube.common.exception.NotFoundException;
import com.example.youtube.playlist.dto.request.CreatePlaylistRequest;
import com.example.youtube.playlist.dto.request.ReorderPlaylistRequest;
import com.example.youtube.playlist.dto.request.UpdatePlaylistRequest;
import com.example.youtube.playlist.dto.response.PlaylistItemResponse;
import com.example.youtube.playlist.dto.response.PlaylistResponse;
import com.example.youtube.playlist.entity.*;
import com.example.youtube.playlist.grpc.client.VideoGrpcClient;
import com.example.youtube.playlist.mapper.PlaylistItemMapper;
import com.example.youtube.playlist.mapper.PlaylistMapper;
import com.example.youtube.playlist.repository.PlaylistItemRepository;
import com.example.youtube.playlist.repository.PlaylistRepository;
import com.example.youtube.playlist.service.PlaylistService;
import com.example.youtube.proto.video.VideoInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private static final String CODE_TRENDING = "TRENDING";

    private final PlaylistRepository playlistRepository;
    private final PlaylistItemRepository playlistItemRepository;
    private final PlaylistMapper playlistMapper;
    private final PlaylistItemMapper playlistItemMapper;
    private final VideoGrpcClient videoGrpcClient;

    @Override
    @Transactional
    public PlaylistResponse createUserPlaylist(CreatePlaylistRequest request, Long userId) {
        PlaylistEntity entity = playlistMapper.toEntity(request);
        entity.setOwnerType(OwnerType.USER);
        entity.setOwnerId(userId);
        entity.setPlaylistType(PlaylistType.USER_CREATED);
        entity.setEditable(true);
        if (entity.getVisibility() == null) {
            entity.setVisibility(Visibility.PRIVATE);
        }
        return toPlaylistResponse(playlistRepository.save(entity));
    }

    @Override
    public PlaylistResponse getPlaylist(Long playlistId, Long currentUserId) {
        PlaylistEntity playlist = findPlaylist(playlistId);
        if (playlist.getOwnerType() == OwnerType.USER
                && playlist.getVisibility() == Visibility.PRIVATE
                && !Objects.equals(playlist.getOwnerId(), currentUserId)) {
            throw new ForbiddenException("Playlist is private");
        }
        return toPlaylistResponse(playlist);
    }

    @Override
    public List<PlaylistResponse> getMyPlaylists(Long userId) {
        ensureDefaultUserPlaylists(userId);
        return playlistRepository.findByOwnerTypeAndOwnerId(OwnerType.USER, userId)
                .stream()
                .map(this::toPlaylistResponse)
                .toList();
    }

    @Override
    @Transactional
    public PlaylistResponse updateUserPlaylist(Long playlistId, UpdatePlaylistRequest request, Long userId) {
        PlaylistEntity playlist = findOwnedUserEditablePlaylist(playlistId, userId);
        if (request.getTitle() != null) {
            playlist.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            playlist.setDescription(request.getDescription());
        }
        if (request.getVisibility() != null) {
            playlist.setVisibility(request.getVisibility());
        }
        return toPlaylistResponse(playlistRepository.save(playlist));
    }

    @Override
    @Transactional
    public void deleteUserPlaylist(Long playlistId, Long userId) {
        PlaylistEntity playlist = findOwnedUserEditablePlaylist(playlistId, userId);
        playlistItemRepository.deleteByPlaylistId(playlistId);
        playlistRepository.delete(playlist);
    }

    @Override
    @Transactional
    public PlaylistResponse addVideoToPlaylist(Long playlistId, Long videoId, Long userId, String source) {
        PlaylistEntity playlist = findOwnedUserEditablePlaylist(playlistId, userId);
        addUniqueVideo(playlist.getId(), videoId, userId, source);
        return toPlaylistResponse(playlist);
    }

    @Override
    @Transactional
    public PlaylistResponse removeVideoFromPlaylist(Long playlistId, Long videoId, Long userId) {
        PlaylistEntity playlist = findOwnedUserEditablePlaylist(playlistId, userId);
        PlaylistItemEntity item = playlistItemRepository.findByPlaylistIdAndVideoId(playlistId, videoId)
                .orElseThrow(() -> new NotFoundException("Playlist item not found"));
        playlistItemRepository.delete(item);
        compactPositions(playlistId);
        return toPlaylistResponse(playlist);
    }

    @Override
    @Transactional
    public PlaylistResponse reorderPlaylistItems(Long playlistId, ReorderPlaylistRequest request, Long userId) {
        PlaylistEntity playlist = findOwnedUserEditablePlaylist(playlistId, userId);
        List<PlaylistItemEntity> items = playlistItemRepository.findByPlaylistIdOrderByPositionAsc(playlistId);
        Map<Long, PlaylistItemEntity> byId = items.stream().collect(Collectors.toMap(PlaylistItemEntity::getId, i -> i));
        for (ReorderPlaylistRequest.ReorderItem reorderItem : request.getItems()) {
            PlaylistItemEntity item = byId.get(reorderItem.getPlaylistItemId());
            if (item == null) {
                throw new BadRequestException("Invalid item in reorder request");
            }
            item.setPosition(reorderItem.getPosition());
        }
        playlistItemRepository.saveAll(items);
        compactPositions(playlistId);
        return toPlaylistResponse(playlist);
    }

    @Override
    public PlaylistResponse getWatchLater(Long userId) {
        ensureDefaultUserPlaylists(userId);
        PlaylistEntity watchLater = playlistRepository
                .findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType.USER, userId, PlaylistType.WATCH_LATER)
                .orElseThrow(() -> new NotFoundException("Watch later playlist not found"));
        return toPlaylistResponse(watchLater);
    }

    @Override
    @Transactional
    public PlaylistResponse addWatchLater(Long userId, Long videoId) {
        ensureDefaultUserPlaylists(userId);
        PlaylistEntity watchLater = playlistRepository
                .findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType.USER, userId, PlaylistType.WATCH_LATER)
                .orElseThrow(() -> new NotFoundException("Watch later playlist not found"));
        addUniqueVideo(watchLater.getId(), videoId, userId, "WATCH_LATER");
        return toPlaylistResponse(watchLater);
    }

    @Override
    @Transactional
    public PlaylistResponse removeWatchLater(Long userId, Long videoId) {
        ensureDefaultUserPlaylists(userId);
        PlaylistEntity watchLater = playlistRepository
                .findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType.USER, userId, PlaylistType.WATCH_LATER)
                .orElseThrow(() -> new NotFoundException("Watch later playlist not found"));
        playlistItemRepository.findByPlaylistIdAndVideoId(watchLater.getId(), videoId)
                .ifPresent(playlistItemRepository::delete);
        compactPositions(watchLater.getId());
        return toPlaylistResponse(watchLater);
    }

    @Override
    public PlaylistResponse getHistory(Long userId) {
        ensureDefaultUserPlaylists(userId);
        PlaylistEntity history = playlistRepository
                .findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType.USER, userId, PlaylistType.HISTORY)
                .orElseThrow(() -> new NotFoundException("History playlist not found"));
        return toPlaylistResponse(history);
    }

    @Override
    @Transactional
    public void clearHistory(Long userId) {
        ensureDefaultUserPlaylists(userId);
        PlaylistEntity history = playlistRepository
                .findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType.USER, userId, PlaylistType.HISTORY)
                .orElseThrow(() -> new NotFoundException("History playlist not found"));
        playlistItemRepository.deleteByPlaylistId(history.getId());
    }

    @Override
    public List<PlaylistResponse> getSystemPlaylists() {
        return playlistRepository.findByOwnerType(OwnerType.SYSTEM)
                .stream()
                .map(this::toPlaylistResponse)
                .toList();
    }

    @Override
    public PlaylistResponse getSystemPlaylistByCode(String code) {
        PlaylistEntity playlist = playlistRepository.findByOwnerTypeAndPlaylistCode(OwnerType.SYSTEM, code)
                .orElseThrow(() -> new NotFoundException("System playlist not found"));
        return toPlaylistResponse(playlist);
    }

    @Override
    @Transactional
    public void ensureDefaultUserPlaylists(Long userId) {
        playlistRepository.findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType.USER, userId, PlaylistType.WATCH_LATER)
                .orElseGet(() -> playlistRepository.save(PlaylistEntity.builder()
                        .title("Watch Later")
                        .description("Auto-generated Watch Later playlist")
                        .ownerType(OwnerType.USER)
                        .ownerId(userId)
                        .playlistType(PlaylistType.WATCH_LATER)
                        .visibility(Visibility.PRIVATE)
                        .editable(true)
                        .build()));
        playlistRepository.findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType.USER, userId, PlaylistType.HISTORY)
                .orElseGet(() -> playlistRepository.save(PlaylistEntity.builder()
                        .title("History")
                        .description("Auto-generated History playlist")
                        .ownerType(OwnerType.USER)
                        .ownerId(userId)
                        .playlistType(PlaylistType.HISTORY)
                        .visibility(Visibility.PRIVATE)
                        .editable(false)
                        .build()));
    }

    @Override
    @Transactional
    public void addHistoryItemInternal(Long userId, Long videoId, LocalDateTime watchedAt) {
        ensureDefaultUserPlaylists(userId);
        PlaylistEntity history = playlistRepository
                .findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType.USER, userId, PlaylistType.HISTORY)
                .orElseThrow(() -> new NotFoundException("History playlist not found"));

        PlaylistItemEntity existing = playlistItemRepository.findByPlaylistIdAndVideoId(history.getId(), videoId).orElse(null);
        if (existing != null) {
            playlistItemRepository.delete(existing);
        }
        shiftAllPositionsDown(history.getId());
        playlistItemRepository.save(PlaylistItemEntity.builder()
                .playlistId(history.getId())
                .videoId(videoId)
                .position(1)
                .source("HISTORY")
                .addedBy(userId)
                .addedAt(watchedAt == null ? LocalDateTime.now() : watchedAt)
                .build());
        compactPositions(history.getId());
    }

    @Override
    @Transactional
    public void updateSystemPlaylistInternal(String playlistCode, List<Long> videoIds) {
        PlaylistEntity systemPlaylist = playlistRepository.findByOwnerTypeAndPlaylistCode(OwnerType.SYSTEM, playlistCode)
                .orElseGet(() -> playlistRepository.save(PlaylistEntity.builder()
                        .title("Trending")
                        .description("Auto-generated system playlist")
                        .ownerType(OwnerType.SYSTEM)
                        .ownerId(null)
                        .playlistType(PlaylistType.SYSTEM_TRENDING)
                        .playlistCode(CODE_TRENDING)
                        .visibility(Visibility.PUBLIC)
                        .editable(false)
                        .build()));

        playlistItemRepository.deleteByPlaylistId(systemPlaylist.getId());
        int position = 1;
        for (Long videoId : videoIds) {
            playlistItemRepository.save(PlaylistItemEntity.builder()
                    .playlistId(systemPlaylist.getId())
                    .videoId(videoId)
                    .position(position++)
                    .source("SYSTEM")
                    .addedBy(null)
                    .addedAt(LocalDateTime.now())
                    .build());
        }
    }

    private PlaylistEntity findOwnedUserEditablePlaylist(Long playlistId, Long userId) {
        PlaylistEntity playlist = findPlaylist(playlistId);
        if (playlist.getOwnerType() != OwnerType.USER || !Objects.equals(playlist.getOwnerId(), userId)) {
            throw new ForbiddenException("Playlist permission denied");
        }
        if (!Boolean.TRUE.equals(playlist.getEditable())) {
            throw new ForbiddenException("Playlist is read-only");
        }
        return playlist;
    }

    private PlaylistEntity findPlaylist(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NotFoundException("Playlist not found: " + playlistId));
    }

    private void addUniqueVideo(Long playlistId, Long videoId, Long userId, String source) {
        if (playlistItemRepository.findByPlaylistIdAndVideoId(playlistId, videoId).isPresent()) {
            throw new ConflictException("Video already exists in playlist");
        }
        int nextPosition = (int) playlistItemRepository.countByPlaylistId(playlistId) + 1;
        playlistItemRepository.save(PlaylistItemEntity.builder()
                .playlistId(playlistId)
                .videoId(videoId)
                .position(nextPosition)
                .source(source)
                .addedBy(userId)
                .addedAt(LocalDateTime.now())
                .build());
    }

    private void compactPositions(Long playlistId) {
        List<PlaylistItemEntity> items = playlistItemRepository.findByPlaylistIdOrderByPositionAsc(playlistId);
        int position = 1;
        for (PlaylistItemEntity item : items) {
            item.setPosition(position++);
        }
        playlistItemRepository.saveAll(items);
    }

    private void shiftAllPositionsDown(Long playlistId) {
        List<PlaylistItemEntity> items = playlistItemRepository.findByPlaylistIdOrderByPositionAsc(playlistId);
        for (PlaylistItemEntity item : items) {
            item.setPosition(item.getPosition() + 1);
        }
        playlistItemRepository.saveAll(items);
    }

    private PlaylistResponse toPlaylistResponse(PlaylistEntity playlist) {
        PlaylistResponse response = playlistMapper.toResponse(playlist);
        List<PlaylistItemEntity> items = playlistItemRepository.findByPlaylistIdOrderByPositionAsc(playlist.getId());
        List<Long> ids = items.stream().map(PlaylistItemEntity::getVideoId).toList();
        Map<Long, VideoInfo> videoMap = videoGrpcClient.getVideosByIds(ids);

        List<PlaylistItemResponse> itemResponses = items.stream().map(item -> {
            PlaylistItemResponse itemResponse = playlistItemMapper.toResponse(item);
            VideoInfo info = videoMap.get(item.getVideoId());
            if (info != null) {
                itemResponse.setVideoTitle(info.getTitle());
                itemResponse.setThumbnailUrl(info.getThumbnailUrl());
            }
            return itemResponse;
        }).toList();
        response.setItems(itemResponses);
        return response;
    }
}
