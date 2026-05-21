package com.example.youtube.aggregation.scheduler;

import com.example.youtube.aggregation.service.AggregationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrendingAggregationJob {
    private final AggregationService aggregationService;

    @Scheduled(fixedDelayString = "${app.jobs.trending.fixed-delay-ms:600000}")
    public void updateTrendingPlaylist() {
        try {
            aggregationService.updateTrendingPlaylist();
        } catch (Exception ex) {
            log.error("Trending aggregation failed", ex);
        }
    }
}
