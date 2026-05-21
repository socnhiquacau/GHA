package com.example.youtube.aggregation.controller;

import com.example.youtube.common.response.ApiResponse;
import com.example.youtube.common.response.ConnectionHealth;
import com.example.youtube.common.response.PingResponse;
import io.grpc.Channel;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/ping")
public class PingController {
    @GrpcClient("videoService")
    private Channel videoServiceChannel;

    @GrpcClient("playlistService")
    private Channel playlistServiceChannel;

    @GetMapping
    public ApiResponse<PingResponse> ping() {
        ConnectionHealth videoGrpcHealth = checkGrpc("video-service-grpc", videoServiceChannel);
        ConnectionHealth playlistGrpcHealth = checkGrpc("playlist-service-grpc", playlistServiceChannel);

        PingResponse response = PingResponse.builder()
                .service("aggregation-worker")
                .healthy(videoGrpcHealth.isUp() && playlistGrpcHealth.isUp())
                .timestamp(Instant.now())
                .connections(List.of(videoGrpcHealth, playlistGrpcHealth))
                .build();
        return ApiResponse.ok(response);
    }

    private ConnectionHealth checkGrpc(String name, Channel channel) {
        if (channel == null) {
            return ConnectionHealth.builder()
                    .name(name)
                    .up(false)
                    .detail("channel is null")
                    .build();
        }
        if (!(channel instanceof ManagedChannel managedChannel)) {
            return ConnectionHealth.builder()
                    .name(name)
                    .up(true)
                    .detail("channel injected")
                    .build();
        }
        ConnectivityState state = managedChannel.getState(true);
        boolean up = state != ConnectivityState.TRANSIENT_FAILURE && state != ConnectivityState.SHUTDOWN;
        return ConnectionHealth.builder()
                .name(name)
                .up(up)
                .detail(state.name())
                .build();
    }
}
