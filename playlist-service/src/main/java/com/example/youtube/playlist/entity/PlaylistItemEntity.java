package com.example.youtube.playlist.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlist_items",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_playlist_video", columnNames = {"playlist_id", "video_id"})
        },
        indexes = {
                @Index(name = "idx_playlist_items_playlist_position", columnList = "playlist_id, position"),
                @Index(name = "idx_playlist_items_video_id", columnList = "video_id")
        })
public class PlaylistItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "playlist_id", nullable = false)
    private Long playlistId;

    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false, length = 30)
    private String source;

    @Column(name = "added_by")
    private Long addedBy;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() {
        if (addedAt == null) {
            addedAt = LocalDateTime.now();
        }
    }
}
