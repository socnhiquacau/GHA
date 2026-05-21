package com.example.youtube.video.controller;

import com.example.youtube.common.response.ApiResponse;
import com.example.youtube.common.response.ConnectionHealth;
import com.example.youtube.common.response.PingResponse;
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

    public PingController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping
    public ApiResponse<PingResponse> ping() {
        ConnectionHealth dbHealth = checkDbConnection();
        PingResponse response = PingResponse.builder()
                .service("video-service")
                .healthy(dbHealth.isUp())
                .timestamp(Instant.now())
                .connections(List.of(dbHealth))
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
}
