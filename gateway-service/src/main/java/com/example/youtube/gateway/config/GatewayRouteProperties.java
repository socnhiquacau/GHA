package com.example.youtube.gateway.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "gateway.routes")
public class GatewayRouteProperties {
    @NotBlank
    private String videoApiBaseUrl = "http://localhost:8081";
    @NotBlank
    private String playlistApiBaseUrl = "http://localhost:8082";

    public String getVideoApiBaseUrl() {
        return videoApiBaseUrl;
    }

    public void setVideoApiBaseUrl(String videoApiBaseUrl) {
        this.videoApiBaseUrl = videoApiBaseUrl;
    }

    public String getPlaylistApiBaseUrl() {
        return playlistApiBaseUrl;
    }

    public void setPlaylistApiBaseUrl(String playlistApiBaseUrl) {
        this.playlistApiBaseUrl = playlistApiBaseUrl;
    }
}
