package com.example.youtube.gateway;

import com.example.youtube.gateway.config.GatewayAuthProperties;
import com.example.youtube.gateway.config.GatewayRouteProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({GatewayAuthProperties.class, GatewayRouteProperties.class})
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
