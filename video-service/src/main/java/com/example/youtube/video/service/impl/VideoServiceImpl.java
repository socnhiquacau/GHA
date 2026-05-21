package com.example.youtube.video.service.impl;

import com.example.youtube.common.exception.NotFoundException;
import com.example.youtube.video.dto.request.CreateVideoRequest;
import com.example.youtube.video.dto.request.RecordViewRequest;
import com.example.youtube.video.dto.request.UpdateVideoRequest;
import com.example.youtube.video.dto.response.TrendingCandidateResponse;
import com.example.youtube.video.dto.response.VideoResponse;
import com.example.youtube.video.entity.VideoEntity;
import com.example.youtube.video.entity.VideoViewEventEntity;
import com.example.youtube.video.mapper.VideoMapper;
import com.example.youtube.video.repository.TrendingVideoProjection;
import com.example.youtube.video.repository.VideoRepository;
import com.example.youtube.video.repository.VideoViewEventRepository;
import com.example.youtube.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final VideoViewEventRepository viewEventRepository;
    private final VideoMapper videoMapper;

    @Override
    @Transactional
    public VideoResponse create(CreateVideoRequest request) {
        VideoEntity entity = videoMapper.toEntity(request);
        return videoMapper.toResponse(videoRepository.save(entity));
    }

    @Override
    public VideoResponse getById(Long id) {
        return videoMapper.toResponse(findEntity(id));
    }

    @Override
    public List<VideoResponse> getByIds(List<Long> ids) {
        return videoRepository.findAllById(ids)
                .stream()
                .map(videoMapper::toResponse)
                .toList();
    }

    @Override
    public List<VideoResponse> search(String q, int page, int size) {
        return videoRepository.findByTitleContainingIgnoreCase(q == null ? "" : q, PageRequest.of(page, size))
                .stream()
                .map(videoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public VideoResponse update(Long id, UpdateVideoRequest request) {
        VideoEntity entity = findEntity(id);
        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getThumbnailUrl() != null) {
            entity.setThumbnailUrl(request.getThumbnailUrl());
        }
        if (request.getVideoUrl() != null) {
            entity.setVideoUrl(request.getVideoUrl());
        }
        if (request.getDurationSec() != null) {
            entity.setDurationSec(request.getDurationSec());
        }
        if (request.getCategoryId() != null) {
            entity.setCategoryId(request.getCategoryId());
        }
        return videoMapper.toResponse(videoRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        VideoEntity entity = findEntity(id);
        videoRepository.delete(entity);
    }

    @Override
    @Transactional
    public void recordView(Long videoId, RecordViewRequest request, Long userId) {
        VideoEntity video = findEntity(videoId);
        video.setViewCount(video.getViewCount() + 1);
        viewEventRepository.save(VideoViewEventEntity.builder()
                .videoId(videoId)
                .userId(userId)
                .watchDurationSec(request == null ? null : request.getWatchDurationSec())
                .viewedAt(LocalDateTime.now())
                .build());
        videoRepository.save(video);
    }

    @Override
    public List<TrendingCandidateResponse> getTrendingCandidates(int limit, int windowHours) {
        LocalDateTime from = LocalDateTime.now().minusHours(Math.max(windowHours, 1));
        List<TrendingVideoProjection> rows = viewEventRepository.findTrendingSince(from, PageRequest.of(0, Math.max(limit, 1)));
        return rows.stream().map(row -> {
            double averageWatchSeconds = row.getAverageWatchSeconds() == null ? 0D : row.getAverageWatchSeconds();
            long views = row.getViews24h() == null ? 0L : row.getViews24h();
            long likes = 0L;
            double score = views + (likes * 2.0) + (averageWatchSeconds * 0.1);
            return TrendingCandidateResponse.builder()
                    .videoId(row.getVideoId())
                    .views24h(views)
                    .likes24h(likes)
                    .averageWatchSeconds(averageWatchSeconds)
                    .score(score)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<VideoEntity> getEntitiesByIds(List<Long> ids) {
        return videoRepository.findAllById(ids);
    }

    private VideoEntity findEntity(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Video not found: " + id));
    }
}
