package com.example.youtube.playlist.mapper;

import com.example.youtube.playlist.dto.response.PlaylistItemResponse;
import com.example.youtube.playlist.entity.PlaylistItemEntity;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

import static org.junit.Assert.*;


public class PlaylistItemMapperTest {

    private final PlaylistItemMapper mapper = Mappers.getMapper(PlaylistItemMapper.class);

    @Test
    public void testToResponse() {
        // Arrange
        PlaylistItemEntity entity = new PlaylistItemEntity();
        entity.setId(1L);

        // Act
        PlaylistItemResponse response = mapper.toResponse(entity);

        // Assert
        assertNotNull(response);
        assertNull(response.getVideoTitle()); // Ignored field
        assertNull(response.getThumbnailUrl()); // Ignored field
    }
    @Test
    public void testToResponse3() {
        // Arrange
        PlaylistItemEntity entity = new PlaylistItemEntity();
        entity.setId(1L);

        // Act
        PlaylistItemResponse response = mapper.toResponse(entity);

        // Assert
        assertNotNull(response);
        assertNull(response.getVideoTitle()); // Ignored field
        assertNull(response.getThumbnailUrl()); // Ignored field
    }
    @Test
    public void testToResponse2() {
        // Arrange
        PlaylistItemEntity entity = new PlaylistItemEntity();
        entity.setId(1L);

        // Act
        PlaylistItemResponse response = mapper.toResponse(entity);

        // Assert
        assertNotNull(response);
        assertNull(response.getVideoTitle()); // Ignored field
        assertNull(response.getThumbnailUrl()); // Ignored field
    }
}
