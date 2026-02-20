package com.samsung.s28utra.productservice.presentation.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ping")
public class PingController {

    private final String appName;
    private final String appVersion;

    public PingController(@Value("${spring.application.name:unknown}") String appName,
                          @Value("${app.version:unknown}") String appVersion) {
        this.appName = appName;
        this.appVersion = appVersion;
    }

    @GetMapping
    public ResponseEntity<PingResponse> ping() {
        PingResponse response = new PingResponse(appName, appVersion);
        return ResponseEntity.ok(response);
    }
}
