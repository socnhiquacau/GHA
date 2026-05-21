package com.example.youtube.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PingResponse {
    private String service;
    private boolean healthy;
    private Instant timestamp;
    private List<ConnectionHealth> connections;
}
