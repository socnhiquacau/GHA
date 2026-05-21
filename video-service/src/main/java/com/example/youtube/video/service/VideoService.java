package com.example.youtube.video.service;

import com.example.youtube.video.dto.request.CreateVideoRequest;
import com.example.youtube.video.dto.request.RecordViewRequest;
import com.example.youtube.video.dto.request.UpdateVideoRequest;
import com.example.youtube.video.dto.response.TrendingCandidateResponse;
import com.example.youtube.video.dto.response.VideoResponse;
import com.example.youtube.video.entity.VideoEntity;

import java.util.List;

public interface VideoService {
    VideoResponse create(CreateVideoRequest request);

    VideoResponse getById(Long id);

    List<VideoResponse> getByIds(List<Long> ids);

    List<VideoResponse> search(String q, int page, int size);

    VideoResponse update(Long id, UpdateVideoRequest request);

    void delete(Long id);

    void recordView(Long videoId, RecordViewRequest request, Long userId);

    List<TrendingCandidateResponse> getTrendingCandidates(int limit, int windowHours);

    List<VideoEntity> getEntitiesByIds(List<Long> ids);
}
