package com.example.youtube.video.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrendingCandidateResponse {
    private Long videoId;
    private Long views24h;
    private Long likes24h;
    private Double averageWatchSeconds;
    private Double score;
}
