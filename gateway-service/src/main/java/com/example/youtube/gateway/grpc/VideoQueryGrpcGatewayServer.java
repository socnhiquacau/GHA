package com.example.youtube.gateway.grpc;

import com.example.youtube.proto.video.*;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class VideoQueryGrpcGatewayServer extends VideoQueryServiceGrpc.VideoQueryServiceImplBase {
    @GrpcClient("videoService")
    private VideoQueryServiceGrpc.VideoQueryServiceBlockingStub videoServiceStub;

    @Override
    public void getVideoById(GetVideoByIdRequest request, StreamObserver<GetVideoByIdResponse> responseObserver) {
        forward(() -> videoServiceStub.getVideoById(request), responseObserver);
    }

    @Override
    public void getVideosByIds(GetVideosByIdsRequest request, StreamObserver<GetVideosByIdsResponse> responseObserver) {
        forward(() -> videoServiceStub.getVideosByIds(request), responseObserver);
    }

    @Override
    public void getTrendingCandidates(GetTrendingCandidatesRequest request,
                                      StreamObserver<GetTrendingCandidatesResponse> responseObserver) {
        forward(() -> videoServiceStub.getTrendingCandidates(request), responseObserver);
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
