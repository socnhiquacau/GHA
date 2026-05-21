package com.example.youtube.playlist.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReorderPlaylistRequest {
    private List<ReorderItem> items;

    @Getter
    @Setter
    public static class ReorderItem {
        private Long playlistItemId;
        private Integer position;
    }
}
