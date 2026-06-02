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
public class VideoServiceImplClone  {
    private final VideoRepository videoRepository;
    private final VideoViewEventRepository viewEventRepository;
    private final VideoMapper videoMapper;
}
