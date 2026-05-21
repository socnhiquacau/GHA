package com.example.youtube.playlist.grpc.client;

import com.example.youtube.proto.video.GetVideosByIdsRequest;
import com.example.youtube.proto.video.VideoInfo;
import com.example.youtube.proto.video.VideoQueryServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VideoGrpcClient {
    @GrpcClient("videoService")
    private VideoQueryServiceGrpc.VideoQueryServiceBlockingStub blockingStub;

    public Map<Long, VideoInfo> getVideosByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }
        var response = blockingStub.getVideosByIds(
                GetVideosByIdsRequest.newBuilder().addAllVideoIds(ids).build());
        Map<Long, VideoInfo> result = new HashMap<>();
        for (VideoInfo video : response.getVideosList()) {
            result.put(video.getId(), video);
        }
        return result;
    }
}
