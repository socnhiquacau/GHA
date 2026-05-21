package com.example.youtube.video.repository;

public interface TrendingVideoProjection {
    Long getVideoId();

    Long getViews24h();

    Double getAverageWatchSeconds();
}
