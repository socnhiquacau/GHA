package com.example.youtube.playlist.mapper;

import com.example.youtube.playlist.dto.request.CreatePlaylistRequest;
import com.example.youtube.playlist.dto.response.PlaylistResponse;
import com.example.youtube.playlist.entity.PlaylistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    @Mapping(target = "ownerType", expression = "java(entity.getOwnerType().name())")
    @Mapping(target = "playlistType", expression = "java(entity.getPlaylistType().name())")
    @Mapping(target = "visibility", expression = "java(entity.getVisibility().name())")
    @Mapping(target = "items", ignore = true)
    PlaylistResponse toResponse(PlaylistEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerType", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "playlistType", ignore = true)
    @Mapping(target = "playlistCode", ignore = true)
    @Mapping(target = "editable", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PlaylistEntity toEntity(CreatePlaylistRequest request);
}
