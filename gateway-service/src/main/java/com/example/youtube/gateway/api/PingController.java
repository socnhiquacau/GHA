package com.example.youtube.gateway.api;

import com.example.youtube.common.response.ApiResponse;
import com.example.youtube.common.response.PingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class PingController {
    @GetMapping("/ping")
    public ApiResponse<PingResponse> ping() {
        return ApiResponse.ok(PingResponse.builder()
                .service("gateway-service")
                .healthy(true)
                .timestamp(Instant.now())
                .connections(List.of())
                .build());
    }
}
