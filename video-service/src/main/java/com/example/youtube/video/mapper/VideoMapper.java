package com.example.youtube.video.mapper;

import com.example.youtube.video.dto.request.CreateVideoRequest;
import com.example.youtube.video.dto.response.VideoResponse;
import com.example.youtube.video.entity.VideoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    VideoResponse toResponse(VideoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    VideoEntity toEntity(CreateVideoRequest request);
}
