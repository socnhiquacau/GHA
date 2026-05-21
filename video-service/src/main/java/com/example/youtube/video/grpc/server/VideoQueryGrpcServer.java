package com.example.youtube.video.grpc.server;

import com.example.youtube.proto.video.*;
import com.example.youtube.video.dto.response.TrendingCandidateResponse;
import com.example.youtube.video.entity.VideoEntity;
import com.example.youtube.video.service.VideoService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.*;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class VideoQueryGrpcServer extends VideoQueryServiceGrpc.VideoQueryServiceImplBase {
    private final VideoService videoService;

    @Override
    public void getVideoById(GetVideoByIdRequest request, StreamObserver<GetVideoByIdResponse> responseObserver) {
        VideoInfo info = toVideoInfo(videoService.getEntitiesByIds(List.of(request.getVideoId())).stream().findFirst().orElse(null));
        responseObserver.onNext(GetVideoByIdResponse.newBuilder().setVideo(info).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getVideosByIds(GetVideosByIdsRequest request, StreamObserver<GetVideosByIdsResponse> responseObserver) {
        List<VideoInfo> videos = videoService.getEntitiesByIds(request.getVideoIdsList())
                .stream()
                .map(this::toVideoInfo)
                .collect(Collectors.toList());
        responseObserver.onNext(GetVideosByIdsResponse.newBuilder().addAllVideos(videos).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getTrendingCandidates(GetTrendingCandidatesRequest request, StreamObserver<GetTrendingCandidatesResponse> responseObserver) {
        List<TrendingCandidateResponse> candidates = videoService.getTrendingCandidates(request.getLimit(), request.getWindowHours());
        List<TrendingCandidate> protoCandidates = candidates.stream()
                .map(c -> TrendingCandidate.newBuilder()
                        .setVideoId(c.getVideoId())
                        .setViews24H(c.getViews24h())
                        .setLikes24H(c.getLikes24h())
                        .setAverageWatchSeconds(c.getAverageWatchSeconds())
                        .setScore(c.getScore())
                        .build())
                .toList();
        responseObserver.onNext(GetTrendingCandidatesResponse.newBuilder().addAllCandidates(protoCandidates).build());
        responseObserver.onCompleted();
    }

    private VideoInfo toVideoInfo(VideoEntity entity) {
        if (entity == null) {
            return VideoInfo.getDefaultInstance();
        }
        return VideoInfo.newBuilder()
                .setId(entity.getId())
                .setTitle(Optional.ofNullable(entity.getTitle()).orElse(""))
                .setDescription(Optional.ofNullable(entity.getDescription()).orElse(""))
                .setThumbnailUrl(Optional.ofNullable(entity.getThumbnailUrl()).orElse(""))
                .setVideoUrl(Optional.ofNullable(entity.getVideoUrl()).orElse(""))
                .setDurationSec(Optional.ofNullable(entity.getDurationSec()).orElse(0))
                .setViewCount(Optional.ofNullable(entity.getViewCount()).orElse(0L))
                .setLikeCount(Optional.ofNullable(entity.getLikeCount()).orElse(0L))
                .setStatus(Optional.ofNullable(entity.getStatus()).map(Enum::name).orElse(""))
                .setUploaderId(Optional.ofNullable(entity.getUploaderId()).orElse(0L))
                .setCategoryId(Optional.ofNullable(entity.getCategoryId()).orElse(0L))
                .build();
    }
}
