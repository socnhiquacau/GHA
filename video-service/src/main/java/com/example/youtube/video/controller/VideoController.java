package com.example.youtube.video.controller;

import com.example.youtube.common.context.RequestContextHolder;
import com.example.youtube.common.response.ApiResponse;
import com.example.youtube.video.dto.request.CreateVideoRequest;
import com.example.youtube.video.dto.request.RecordViewRequest;
import com.example.youtube.video.dto.request.UpdateVideoRequest;
import com.example.youtube.video.dto.response.TrendingCandidateResponse;
import com.example.youtube.video.dto.response.VideoResponse;
import com.example.youtube.video.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
public class VideoController {
    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<ApiResponse<VideoResponse>> create(@RequestBody @Valid CreateVideoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(videoService.create(request)));
    }

    @GetMapping("/{videoId}")
    public ApiResponse<VideoResponse> getById(@PathVariable Long videoId) {
        return ApiResponse.ok(videoService.getById(videoId));
    }

    @GetMapping
    public ApiResponse<List<VideoResponse>> getByIds(@RequestParam(name = "ids") List<Long> ids) {
        return ApiResponse.ok(videoService.getByIds(ids));
    }

    @GetMapping("/search")
    public ApiResponse<List<VideoResponse>> search(@RequestParam(name = "q", required = false) String q,
                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                   @RequestParam(name = "size", defaultValue = "20") int size) {
        return ApiResponse.ok(videoService.search(q, page, size));
    }

    @PostMapping("/{videoId}/views")
    public ApiResponse<Void> recordView(@PathVariable Long videoId,
                                        @RequestBody(required = false) RecordViewRequest request) {
        Long userId = RequestContextHolder.get() == null ? null : RequestContextHolder.get().getUserId();
        videoService.recordView(videoId, request, userId);
        return ApiResponse.ok(null);
    }

    @PutMapping("/{videoId}")
    public ApiResponse<VideoResponse> update(@PathVariable Long videoId, @RequestBody UpdateVideoRequest request) {
        return ApiResponse.ok(videoService.update(videoId, request));
    }

    @DeleteMapping("/{videoId}")
    public ApiResponse<Void> delete(@PathVariable Long videoId) {
        videoService.delete(videoId);
        return ApiResponse.ok(null);
    }

    @GetMapping("/internal/trending-candidates")
    public ApiResponse<List<TrendingCandidateResponse>> trendingCandidates(
            @RequestParam(name = "limit", defaultValue = "50") int limit,
            @RequestParam(name = "windowHours", defaultValue = "24") int windowHours) {
        return ApiResponse.ok(videoService.getTrendingCandidates(limit, windowHours));
    }
}
