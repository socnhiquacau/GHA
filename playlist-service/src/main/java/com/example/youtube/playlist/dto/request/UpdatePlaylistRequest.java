package com.example.youtube.playlist.dto.request;

import com.example.youtube.playlist.entity.Visibility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlaylistRequest {
    private String title;
    private String description;
    private Visibility visibility;
}
