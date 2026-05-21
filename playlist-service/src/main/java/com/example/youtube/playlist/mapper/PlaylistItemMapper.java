package com.example.youtube.playlist.mapper;

import com.example.youtube.playlist.dto.response.PlaylistItemResponse;
import com.example.youtube.playlist.entity.PlaylistItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaylistItemMapper {
    @Mapping(target = "playlistItemId", source = "id")
    @Mapping(target = "videoTitle", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    PlaylistItemResponse toResponse(PlaylistItemEntity entity);
}
