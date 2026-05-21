package com.example.youtube.video.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoResponse {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String videoUrl;
    private Integer durationSec;
    private Long viewCount;
    private Long likeCount;
    private String status;
    private Long uploaderId;
    private Long categoryId;
}
