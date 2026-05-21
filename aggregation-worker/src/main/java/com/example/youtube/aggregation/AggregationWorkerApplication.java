package com.example.youtube.aggregation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.example.youtube.aggregation", "com.example.youtube.common"})
public class AggregationWorkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AggregationWorkerApplication.class, args);
    }
}
