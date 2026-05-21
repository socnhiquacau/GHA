package com.example.youtube.playlist.repository;

import com.example.youtube.playlist.entity.PlaylistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItemEntity, Long> {
    List<PlaylistItemEntity> findByPlaylistIdOrderByPositionAsc(Long playlistId);

    Optional<PlaylistItemEntity> findByPlaylistIdAndVideoId(Long playlistId, Long videoId);

    long countByPlaylistId(Long playlistId);

    void deleteByPlaylistId(Long playlistId);
}
