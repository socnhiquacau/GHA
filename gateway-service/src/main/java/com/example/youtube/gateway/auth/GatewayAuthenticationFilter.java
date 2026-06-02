package com.example.youtube.gateway.auth;

import com.example.youtube.common.exception.UnauthorizedException;
import com.example.youtube.common.header.HeaderConstants;
import com.example.youtube.common.response.ApiResponse;
import com.example.youtube.common.response.ErrorCode;
import com.example.youtube.gateway.config.GatewayAuthProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayAuthenticationFilter extends OncePerRequestFilter {
    private final GatewayAuthProperties authProperties;
    private final CookieAuthenticationService cookieAuthenticationService;
    private final ObjectMapper objectMapper;

    public GatewayAuthenticationFilter(GatewayAuthProperties authProperties,
                                       CookieAuthenticationService cookieAuthenticationService,
                                       ObjectMapper objectMapper) {
        this.authProperties = authProperties;
        this.cookieAuthenticationService = cookieAuthenticationService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (!StringUtils.hasText(path)) {
            return true;
        }
        if (!path.startsWith(authProperties.getRequiredApiPathPrefix())) {
            return true;
        }
        return authProperties.getExcludedPaths().stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            AuthenticatedUser authenticatedUser = cookieAuthenticationService.authenticateHttpCookie(
                    request.getHeader("Cookie"),
                    request.getHeader(HeaderConstants.REQUEST_ID));

            request.setAttribute(GatewayRequestAttribute.AUTHENTICATED_USER, authenticatedUser);
            response.setHeader(HeaderConstants.REQUEST_ID, authenticatedUser.requestId());
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(
                    ApiResponse.error(ex.getMessage(), ErrorCode.UNAUTHORIZED)));
        }
    }
}
