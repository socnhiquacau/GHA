package com.example.youtube.video.mapper;

import com.example.youtube.video.dto.request.CreateVideoRequest;
import com.example.youtube.video.dto.response.VideoResponse;
import com.example.youtube.video.entity.VideoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-21T20:20:25+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class VideoMapperImpl implements VideoMapper {

    @Override
    public VideoResponse toResponse(VideoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        VideoResponse videoResponse = new VideoResponse();

        videoResponse.setCategoryId( entity.getCategoryId() );
        videoResponse.setDescription( entity.getDescription() );
        videoResponse.setDurationSec( entity.getDurationSec() );
        videoResponse.setId( entity.getId() );
        videoResponse.setLikeCount( entity.getLikeCount() );
        if ( entity.getStatus() != null ) {
            videoResponse.setStatus( entity.getStatus().name() );
        }
        videoResponse.setThumbnailUrl( entity.getThumbnailUrl() );
        videoResponse.setTitle( entity.getTitle() );
        videoResponse.setUploaderId( entity.getUploaderId() );
        videoResponse.setVideoUrl( entity.getVideoUrl() );
        videoResponse.setViewCount( entity.getViewCount() );

        return videoResponse;
    }

    @Override
    public VideoEntity toEntity(CreateVideoRequest request) {
        if ( request == null ) {
            return null;
        }

        VideoEntity.VideoEntityBuilder videoEntity = VideoEntity.builder();

        videoEntity.categoryId( request.getCategoryId() );
        videoEntity.description( request.getDescription() );
        videoEntity.durationSec( request.getDurationSec() );
        videoEntity.thumbnailUrl( request.getThumbnailUrl() );
        videoEntity.title( request.getTitle() );
        videoEntity.uploaderId( request.getUploaderId() );
        videoEntity.videoUrl( request.getVideoUrl() );

        return videoEntity.build();
    }
}
