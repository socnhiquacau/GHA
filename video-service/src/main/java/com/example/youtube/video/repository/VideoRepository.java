package com.example.youtube.video.repository;

import com.example.youtube.video.entity.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    Page<VideoEntity> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
