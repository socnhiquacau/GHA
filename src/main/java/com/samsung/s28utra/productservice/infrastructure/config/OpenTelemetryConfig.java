package com.samsung.s28utra.productservice.infrastructure.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OpenTelemetryConfig {

    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {
        log.info("Initializing OpenTelemetry Tracer for service: {}", serviceName);
        return openTelemetry.getTracer(serviceName);
    }
}