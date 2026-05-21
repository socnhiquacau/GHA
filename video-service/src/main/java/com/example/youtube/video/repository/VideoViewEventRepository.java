package com.example.youtube.video.repository;

import com.example.youtube.video.entity.VideoViewEventEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VideoViewEventRepository extends JpaRepository<VideoViewEventEntity, Long> {
    @Query("""
            select e.videoId as videoId,
                   count(e) as views24h,
                   coalesce(avg(e.watchDurationSec), 0) as averageWatchSeconds
            from VideoViewEventEntity e
            where e.viewedAt >= :fromTime
            group by e.videoId
            order by count(e) desc
            """)
    List<TrendingVideoProjection> findTrendingSince(@Param("fromTime") LocalDateTime fromTime, Pageable pageable);
}
