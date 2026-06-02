package com.example.youtube.gateway.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties(prefix = "gateway.auth")
public class GatewayAuthProperties {
    @NotBlank
    private String cookieName = "AUTH_USER";
    @NotBlank
    private String requiredApiPathPrefix = "/api";
    private List<String> excludedPaths = List.of("/ping", "/actuator/health");

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getRequiredApiPathPrefix() {
        return requiredApiPathPrefix;
    }

    public void setRequiredApiPathPrefix(String requiredApiPathPrefix) {
        this.requiredApiPathPrefix = requiredApiPathPrefix;
    }

    public List<String> getExcludedPaths() {
        return excludedPaths;
    }

    public void setExcludedPaths(List<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }
}
