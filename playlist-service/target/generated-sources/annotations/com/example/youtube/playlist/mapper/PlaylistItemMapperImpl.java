package com.example.youtube.playlist.mapper;

import com.example.youtube.playlist.dto.response.PlaylistItemResponse;
import com.example.youtube.playlist.entity.PlaylistItemEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-21T20:20:23+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class PlaylistItemMapperImpl implements PlaylistItemMapper {

    @Override
    public PlaylistItemResponse toResponse(PlaylistItemEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PlaylistItemResponse playlistItemResponse = new PlaylistItemResponse();

        playlistItemResponse.setPlaylistItemId( entity.getId() );
        playlistItemResponse.setAddedAt( entity.getAddedAt() );
        playlistItemResponse.setPlaylistId( entity.getPlaylistId() );
        playlistItemResponse.setPosition( entity.getPosition() );
        playlistItemResponse.setSource( entity.getSource() );
        playlistItemResponse.setVideoId( entity.getVideoId() );

        return playlistItemResponse;
    }
}
