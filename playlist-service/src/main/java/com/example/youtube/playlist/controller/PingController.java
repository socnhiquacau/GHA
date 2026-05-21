package com.example.youtube.playlist.controller;

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

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/ping")
public class PingController {
    private final DataSource dataSource;

    @GrpcClient("videoService")
    private Channel videoServiceChannel;

    public PingController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping
    public ApiResponse<PingResponse> ping() {
        ConnectionHealth dbHealth = checkDbConnection();
        ConnectionHealth grpcHealth = checkVideoGrpcConnection();
        boolean healthy = dbHealth.isUp() && grpcHealth.isUp();
        PingResponse response = PingResponse.builder()
                .service("playlist-service")
                .healthy(healthy)
                .timestamp(Instant.now())
                .connections(List.of(dbHealth, grpcHealth))
                .build();
        return ApiResponse.ok(response);
    }

    private ConnectionHealth checkDbConnection() {
        try (Connection connection = dataSource.getConnection()) {
            boolean valid = connection.isValid(2);
            return ConnectionHealth.builder()
                    .name("mariadb")
                    .up(valid)
                    .detail(valid ? "connected" : "invalid connection")
                    .build();
        } catch (Exception ex) {
            return ConnectionHealth.builder()
                    .name("mariadb")
                    .up(false)
                    .detail(ex.getMessage())
                    .build();
        }
    }

    private ConnectionHealth checkVideoGrpcConnection() {
        if (videoServiceChannel == null) {
            return ConnectionHealth.builder()
                    .name("video-service-grpc")
                    .up(false)
                    .detail("channel is null")
                    .build();
        }
        if (!(videoServiceChannel instanceof ManagedChannel managedChannel)) {
            return ConnectionHealth.builder()
                    .name("video-service-grpc")
                    .up(true)
                    .detail("channel injected")
                    .build();
        }
        ConnectivityState state = managedChannel.getState(true);
        boolean up = state != ConnectivityState.TRANSIENT_FAILURE && state != ConnectivityState.SHUTDOWN;
        return ConnectionHealth.builder()
                .name("video-service-grpc")
                .up(up)
                .detail(state.name())
                .build();
    }
}
