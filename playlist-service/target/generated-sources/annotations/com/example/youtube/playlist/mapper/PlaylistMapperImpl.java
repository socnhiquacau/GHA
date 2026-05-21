package com.example.youtube.playlist.mapper;

import com.example.youtube.playlist.dto.request.CreatePlaylistRequest;
import com.example.youtube.playlist.dto.response.PlaylistResponse;
import com.example.youtube.playlist.entity.PlaylistEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-21T20:20:23+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class PlaylistMapperImpl implements PlaylistMapper {

    @Override
    public PlaylistResponse toResponse(PlaylistEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PlaylistResponse playlistResponse = new PlaylistResponse();

        playlistResponse.setDescription( entity.getDescription() );
        playlistResponse.setEditable( entity.getEditable() );
        playlistResponse.setId( entity.getId() );
        playlistResponse.setOwnerId( entity.getOwnerId() );
        playlistResponse.setPlaylistCode( entity.getPlaylistCode() );
        playlistResponse.setTitle( entity.getTitle() );

        playlistResponse.setOwnerType( entity.getOwnerType().name() );
        playlistResponse.setPlaylistType( entity.getPlaylistType().name() );
        playlistResponse.setVisibility( entity.getVisibility().name() );

        return playlistResponse;
    }

    @Override
    public PlaylistEntity toEntity(CreatePlaylistRequest request) {
        if ( request == null ) {
            return null;
        }

        PlaylistEntity.PlaylistEntityBuilder playlistEntity = PlaylistEntity.builder();

        playlistEntity.description( request.getDescription() );
        playlistEntity.title( request.getTitle() );
        playlistEntity.visibility( request.getVisibility() );

        return playlistEntity.build();
    }
}
