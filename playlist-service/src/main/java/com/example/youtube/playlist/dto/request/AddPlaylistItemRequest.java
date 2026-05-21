package com.example.youtube.playlist.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPlaylistItemRequest {
    @NotNull
    private Long videoId;
}
