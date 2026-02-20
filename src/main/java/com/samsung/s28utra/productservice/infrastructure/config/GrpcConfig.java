package com.samsung.s28utra.productservice.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class GrpcConfig {

    public GrpcConfig() {
        log.info("gRPC Configuration initialized");
    }
}