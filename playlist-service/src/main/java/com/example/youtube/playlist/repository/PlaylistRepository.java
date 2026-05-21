package com.example.youtube.playlist.repository;

import com.example.youtube.playlist.entity.OwnerType;
import com.example.youtube.playlist.entity.PlaylistEntity;
import com.example.youtube.playlist.entity.PlaylistType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
    List<PlaylistEntity> findByOwnerTypeAndOwnerId(OwnerType ownerType, Long ownerId);

    Optional<PlaylistEntity> findByOwnerTypeAndOwnerIdAndPlaylistType(OwnerType ownerType, Long ownerId, PlaylistType playlistType);

    Optional<PlaylistEntity> findByOwnerTypeAndPlaylistCode(OwnerType ownerType, String playlistCode);

    List<PlaylistEntity> findByOwnerType(OwnerType ownerType);
}
