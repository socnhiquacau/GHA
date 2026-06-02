package com.example.youtube.gateway.auth;

import com.example.youtube.common.exception.UnauthorizedException;
import com.example.youtube.gateway.config.GatewayAuthProperties;
import io.grpc.Metadata;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class CookieAuthenticationService {
    private static final String DEFAULT_ROLE = "USER";
    private static final Metadata.Key<String> GRPC_COOKIE_HEADER =
            Metadata.Key.of("cookie", Metadata.ASCII_STRING_MARSHALLER);

    private final GatewayAuthProperties authProperties;

    public CookieAuthenticationService(GatewayAuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    public AuthenticatedUser authenticateHttpCookie(String cookieHeader, String requestIdHeader) {
        String rawCookieValue = extractCookieValue(cookieHeader, authProperties.getCookieName());
        return parseCookieToken(rawCookieValue, requestIdHeader);
    }

    public AuthenticatedUser authenticateGrpcCookie(Metadata metadata) {
        String cookieHeader = metadata.get(GRPC_COOKIE_HEADER);
        String requestId = metadata.get(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER));
        String rawCookieValue = extractCookieValue(cookieHeader, authProperties.getCookieName());
        return parseCookieToken(rawCookieValue, requestId);
    }

    private AuthenticatedUser parseCookieToken(String rawCookieValue, String requestIdHeader) {
        if (!StringUtils.hasText(rawCookieValue)) {
            throw new UnauthorizedException("Missing authentication cookie");
        }

        String decoded = URLDecoder.decode(rawCookieValue, StandardCharsets.UTF_8);
        String[] parts = decoded.split(":", 2);
        if (parts.length == 0 || !StringUtils.hasText(parts[0])) {
            throw new UnauthorizedException("Invalid authentication cookie format");
        }

        Long userId;
        try {
            userId = Long.parseLong(parts[0].trim());
        } catch (NumberFormatException ex) {
            throw new UnauthorizedException("Invalid user id in authentication cookie");
        }

        String role = parts.length > 1 && StringUtils.hasText(parts[1]) ? parts[1].trim() : DEFAULT_ROLE;
        String requestId = StringUtils.hasText(requestIdHeader) ? requestIdHeader : UUID.randomUUID().toString();
        return new AuthenticatedUser(userId, role, requestId);
    }

    private String extractCookieValue(String cookieHeader, String cookieName) {
        if (!StringUtils.hasText(cookieHeader)) {
            throw new UnauthorizedException("Missing authentication cookie");
        }
        String[] cookies = cookieHeader.split(";");
        for (String cookie : cookies) {
            String[] kv = cookie.trim().split("=", 2);
            if (kv.length == 2 && cookieName.equals(kv[0].trim())) {
                return kv[1];
            }
        }
        throw new UnauthorizedException("Missing authentication cookie");
    }
}
