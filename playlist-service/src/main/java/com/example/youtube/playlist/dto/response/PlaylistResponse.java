package com.example.youtube.playlist.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistResponse {
    private Long id;
    private String title;
    private String description;
    private String ownerType;
    private Long ownerId;
    private String playlistType;
    private String playlistCode;
    private String visibility;
    private Boolean editable;
    private List<PlaylistItemResponse> items;
}
