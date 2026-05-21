package com.example.youtube.video.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVideoRequest {
    private String title;
    private String description;
    private String thumbnailUrl;
    private String videoUrl;
    private Integer durationSec;
    private Long categoryId;
}
