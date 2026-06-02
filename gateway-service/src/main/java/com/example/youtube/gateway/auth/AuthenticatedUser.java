package com.example.youtube.gateway.auth;

public record AuthenticatedUser(Long userId, String role, String requestId) {
}
