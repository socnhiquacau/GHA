package com.example.youtube.playlist.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaylistItemResponse {
    private Long playlistItemId;
    private Long playlistId;
    private Long videoId;
    private Integer position;
    private String source;
    private LocalDateTime addedAt;
    private String videoTitle;
    private String thumbnailUrl;
}
