package com.example.youtube.playlist.grpc.server;

import com.example.youtube.playlist.service.PlaylistService;
import com.example.youtube.proto.common.CommonStatus;
import com.example.youtube.proto.playlist.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@GrpcService
@RequiredArgsConstructor
public class PlaylistInternalGrpcServer extends PlaylistInternalServiceGrpc.PlaylistInternalServiceImplBase {
    private final PlaylistService playlistService;

    @Override
    public void ensureDefaultUserPlaylists(EnsureDefaultUserPlaylistsRequest request,
                                           StreamObserver<EnsureDefaultUserPlaylistsResponse> responseObserver) {
        playlistService.ensureDefaultUserPlaylists(request.getUserId());
        responseObserver.onNext(EnsureDefaultUserPlaylistsResponse.newBuilder()
                .setStatus(okStatus())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void addHistoryItem(AddHistoryItemRequest request, StreamObserver<AddHistoryItemResponse> responseObserver) {
        LocalDateTime watchedAt = parseDateTime(request.getWatchedAt());
        playlistService.addHistoryItemInternal(request.getUserId(), request.getVideoId(), watchedAt);
        responseObserver.onNext(AddHistoryItemResponse.newBuilder()
                .setStatus(okStatus())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateSystemPlaylist(UpdateSystemPlaylistRequest request,
                                     StreamObserver<UpdateSystemPlaylistResponse> responseObserver) {
        playlistService.updateSystemPlaylistInternal(request.getPlaylistCode(), request.getVideoIdsList());
        responseObserver.onNext(UpdateSystemPlaylistResponse.newBuilder()
                .setStatus(okStatus())
                .build());
        responseObserver.onCompleted();
    }

    private CommonStatus okStatus() {
        return CommonStatus.newBuilder().setSuccess(true).setMessage("OK").build();
    }

    private LocalDateTime parseDateTime(String value) {
        if (value == null || value.isBlank()) {
            return LocalDateTime.now();
        }
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException ex) {
            return LocalDateTime.now();
        }
    }
}
