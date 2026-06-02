package com.example.youtube.gateway.api;

import com.example.youtube.common.exception.NotFoundException;
import com.example.youtube.gateway.config.GatewayRouteProperties;
import org.springframework.stereotype.Component;

@Component
public class ApiRouteResolver {
    private final GatewayRouteProperties routeProperties;

    public ApiRouteResolver(GatewayRouteProperties routeProperties) {
        this.routeProperties = routeProperties;
    }

    public String resolveBaseUrl(String requestPath) {
        if (requestPath.startsWith("/api/videos")) {
            return routeProperties.getVideoApiBaseUrl();
        }
        if (requestPath.startsWith("/api/playlists")
                || requestPath.startsWith("/api/system-playlists")
                || requestPath.startsWith("/api/watch-later")
                || requestPath.startsWith("/api/history")) {
            return routeProperties.getPlaylistApiBaseUrl();
        }
        throw new NotFoundException("No downstream route configured for path: " + requestPath);
    }
}
