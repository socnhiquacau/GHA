package com.example.youtube.aggregation.service.impl;

import com.example.youtube.aggregation.service.AggregationService;
import com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest;
import com.example.youtube.proto.playlist.PlaylistInternalServiceGrpc;
import com.example.youtube.proto.video.GetTrendingCandidatesRequest;
import com.example.youtube.proto.video.VideoQueryServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregationServiceImpl implements AggregationService {
    @GrpcClient("videoService")
    private VideoQueryServiceGrpc.VideoQueryServiceBlockingStub videoStub;

    @GrpcClient("playlistService")
    private PlaylistInternalServiceGrpc.PlaylistInternalServiceBlockingStub playlistStub;

    @Override
    public void updateTrendingPlaylist() {
        var candidatesResponse = videoStub.getTrendingCandidates(GetTrendingCandidatesRequest.newBuilder()
                .setLimit(50)
                .setWindowHours(24)
                .build());

        List<Long> rankedVideoIds = candidatesResponse.getCandidatesList()
                .stream()
                .sorted(Comparator.comparingDouble(c -> -c.getScore()))
                .map(c -> c.getVideoId())
                .toList();

        playlistStub.updateSystemPlaylist(UpdateSystemPlaylistRequest.newBuilder()
                .setPlaylistCode("TRENDING")
                .addAllVideoIds(rankedVideoIds)
                .build());

        log.info("Updated TRENDING system playlist with {} videos", rankedVideoIds.size());
    }
}
