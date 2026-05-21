package com.example.youtube.playlist.dto.request;

import com.example.youtube.playlist.entity.Visibility;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePlaylistRequest {
    @NotBlank
    private String title;
    private String description;
    private Visibility visibility = Visibility.PRIVATE;
}
