package com.example.youtube.gateway.api;

import com.example.youtube.common.exception.UnauthorizedException;
import com.example.youtube.gateway.auth.AuthenticatedUser;
import com.example.youtube.gateway.auth.GatewayRequestAttribute;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiProxyController {
    private final ApiProxyService apiProxyService;

    public ApiProxyController(ApiProxyService apiProxyService) {
        this.apiProxyService = apiProxyService;
    }

    @RequestMapping(
            value = {
                    "/api/videos", "/api/videos/**",
                    "/api/playlists", "/api/playlists/**",
                    "/api/system-playlists", "/api/system-playlists/**",
                    "/api/watch-later", "/api/watch-later/**",
                    "/api/history", "/api/history/**"
            },
            method = {
                    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
                    RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD
            })
    public ResponseEntity<byte[]> forward(HttpServletRequest request,
                                          @RequestBody(required = false) byte[] body) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) request.getAttribute(GatewayRequestAttribute.AUTHENTICATED_USER);
        if (authenticatedUser == null) {
            throw new UnauthorizedException("Missing authenticated user");
        }

        HttpHeaders headers = apiProxyService.extractHeaders(request.getHeaderNames(), request::getHeaders);
        return apiProxyService.forward(
                request.getRequestURI(),
                request.getQueryString(),
                request.getMethod(),
                headers,
                body,
                authenticatedUser);
    }
}
