package com.example.youtube.video.service;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VideoQueueListener {

    @SqsListener("${app.sqs.video-queue}")
    public void receiveMessage(String message) {
        log.info("Received message from SQS: {}", message);

        // TODO: xử lý logic ở đây
        // Ví dụ:
        // - parse JSON
        // - gọi service xử lý video
        // - lưu DB
    }
}