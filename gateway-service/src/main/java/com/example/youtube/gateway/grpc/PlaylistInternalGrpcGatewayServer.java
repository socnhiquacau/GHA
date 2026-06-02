package com.example.youtube.gateway.grpc;

import com.example.youtube.proto.playlist.*;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PlaylistInternalGrpcGatewayServer extends PlaylistInternalServiceGrpc.PlaylistInternalServiceImplBase {
    @GrpcClient("playlistService")
    private PlaylistInternalServiceGrpc.PlaylistInternalServiceBlockingStub playlistServiceStub;

    @Override
    public void ensureDefaultUserPlaylists(EnsureDefaultUserPlaylistsRequest request,
                                           StreamObserver<EnsureDefaultUserPlaylistsResponse> responseObserver) {
        forward(() -> playlistServiceStub.ensureDefaultUserPlaylists(request), responseObserver);
    }

    @Override
    public void addHistoryItem(AddHistoryItemRequest request, StreamObserver<AddHistoryItemResponse> responseObserver) {
        forward(() -> playlistServiceStub.addHistoryItem(request), responseObserver);
    }

    @Override
    public void updateSystemPlaylist(UpdateSystemPlaylistRequest request,
                                     StreamObserver<UpdateSystemPlaylistResponse> responseObserver) {
        forward(() -> playlistServiceStub.updateSystemPlaylist(request), responseObserver);
    }

    private <T> void forward(GrpcCall<T> grpcCall, StreamObserver<T> responseObserver) {
        try {
            responseObserver.onNext(grpcCall.execute());
            responseObserver.onCompleted();
        } catch (StatusRuntimeException ex) {
            responseObserver.onError(ex);
        }
    }

    @FunctionalInterface
    private interface GrpcCall<T> {
        T execute();
    }
}
