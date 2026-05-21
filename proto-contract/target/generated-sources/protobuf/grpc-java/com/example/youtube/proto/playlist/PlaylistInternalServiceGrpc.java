package com.example.youtube.proto.playlist;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.0)",
    comments = "Source: playlist.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PlaylistInternalServiceGrpc {

  private PlaylistInternalServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "youtube.playlist.PlaylistInternalService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest,
      com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse> getEnsureDefaultUserPlaylistsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EnsureDefaultUserPlaylists",
      requestType = com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest.class,
      responseType = com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest,
      com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse> getEnsureDefaultUserPlaylistsMethod() {
    io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest, com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse> getEnsureDefaultUserPlaylistsMethod;
    if ((getEnsureDefaultUserPlaylistsMethod = PlaylistInternalServiceGrpc.getEnsureDefaultUserPlaylistsMethod) == null) {
      synchronized (PlaylistInternalServiceGrpc.class) {
        if ((getEnsureDefaultUserPlaylistsMethod = PlaylistInternalServiceGrpc.getEnsureDefaultUserPlaylistsMethod) == null) {
          PlaylistInternalServiceGrpc.getEnsureDefaultUserPlaylistsMethod = getEnsureDefaultUserPlaylistsMethod =
              io.grpc.MethodDescriptor.<com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest, com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EnsureDefaultUserPlaylists"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlaylistInternalServiceMethodDescriptorSupplier("EnsureDefaultUserPlaylists"))
              .build();
        }
      }
    }
    return getEnsureDefaultUserPlaylistsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.AddHistoryItemRequest,
      com.example.youtube.proto.playlist.AddHistoryItemResponse> getAddHistoryItemMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddHistoryItem",
      requestType = com.example.youtube.proto.playlist.AddHistoryItemRequest.class,
      responseType = com.example.youtube.proto.playlist.AddHistoryItemResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.AddHistoryItemRequest,
      com.example.youtube.proto.playlist.AddHistoryItemResponse> getAddHistoryItemMethod() {
    io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.AddHistoryItemRequest, com.example.youtube.proto.playlist.AddHistoryItemResponse> getAddHistoryItemMethod;
    if ((getAddHistoryItemMethod = PlaylistInternalServiceGrpc.getAddHistoryItemMethod) == null) {
      synchronized (PlaylistInternalServiceGrpc.class) {
        if ((getAddHistoryItemMethod = PlaylistInternalServiceGrpc.getAddHistoryItemMethod) == null) {
          PlaylistInternalServiceGrpc.getAddHistoryItemMethod = getAddHistoryItemMethod =
              io.grpc.MethodDescriptor.<com.example.youtube.proto.playlist.AddHistoryItemRequest, com.example.youtube.proto.playlist.AddHistoryItemResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddHistoryItem"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.playlist.AddHistoryItemRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.playlist.AddHistoryItemResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlaylistInternalServiceMethodDescriptorSupplier("AddHistoryItem"))
              .build();
        }
      }
    }
    return getAddHistoryItemMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest,
      com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse> getUpdateSystemPlaylistMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateSystemPlaylist",
      requestType = com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest.class,
      responseType = com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest,
      com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse> getUpdateSystemPlaylistMethod() {
    io.grpc.MethodDescriptor<com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest, com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse> getUpdateSystemPlaylistMethod;
    if ((getUpdateSystemPlaylistMethod = PlaylistInternalServiceGrpc.getUpdateSystemPlaylistMethod) == null) {
      synchronized (PlaylistInternalServiceGrpc.class) {
        if ((getUpdateSystemPlaylistMethod = PlaylistInternalServiceGrpc.getUpdateSystemPlaylistMethod) == null) {
          PlaylistInternalServiceGrpc.getUpdateSystemPlaylistMethod = getUpdateSystemPlaylistMethod =
              io.grpc.MethodDescriptor.<com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest, com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateSystemPlaylist"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PlaylistInternalServiceMethodDescriptorSupplier("UpdateSystemPlaylist"))
              .build();
        }
      }
    }
    return getUpdateSystemPlaylistMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PlaylistInternalServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlaylistInternalServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlaylistInternalServiceStub>() {
        @java.lang.Override
        public PlaylistInternalServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlaylistInternalServiceStub(channel, callOptions);
        }
      };
    return PlaylistInternalServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PlaylistInternalServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlaylistInternalServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlaylistInternalServiceBlockingStub>() {
        @java.lang.Override
        public PlaylistInternalServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlaylistInternalServiceBlockingStub(channel, callOptions);
        }
      };
    return PlaylistInternalServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PlaylistInternalServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PlaylistInternalServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PlaylistInternalServiceFutureStub>() {
        @java.lang.Override
        public PlaylistInternalServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PlaylistInternalServiceFutureStub(channel, callOptions);
        }
      };
    return PlaylistInternalServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void ensureDefaultUserPlaylists(com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnsureDefaultUserPlaylistsMethod(), responseObserver);
    }

    /**
     */
    default void addHistoryItem(com.example.youtube.proto.playlist.AddHistoryItemRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.AddHistoryItemResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddHistoryItemMethod(), responseObserver);
    }

    /**
     */
    default void updateSystemPlaylist(com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateSystemPlaylistMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PlaylistInternalService.
   */
  public static abstract class PlaylistInternalServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PlaylistInternalServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PlaylistInternalService.
   */
  public static final class PlaylistInternalServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PlaylistInternalServiceStub> {
    private PlaylistInternalServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlaylistInternalServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlaylistInternalServiceStub(channel, callOptions);
    }

    /**
     */
    public void ensureDefaultUserPlaylists(com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEnsureDefaultUserPlaylistsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addHistoryItem(com.example.youtube.proto.playlist.AddHistoryItemRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.AddHistoryItemResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddHistoryItemMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateSystemPlaylist(com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateSystemPlaylistMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PlaylistInternalService.
   */
  public static final class PlaylistInternalServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PlaylistInternalServiceBlockingStub> {
    private PlaylistInternalServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlaylistInternalServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlaylistInternalServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse ensureDefaultUserPlaylists(com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEnsureDefaultUserPlaylistsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.youtube.proto.playlist.AddHistoryItemResponse addHistoryItem(com.example.youtube.proto.playlist.AddHistoryItemRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddHistoryItemMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse updateSystemPlaylist(com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateSystemPlaylistMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PlaylistInternalService.
   */
  public static final class PlaylistInternalServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PlaylistInternalServiceFutureStub> {
    private PlaylistInternalServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlaylistInternalServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PlaylistInternalServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse> ensureDefaultUserPlaylists(
        com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEnsureDefaultUserPlaylistsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.youtube.proto.playlist.AddHistoryItemResponse> addHistoryItem(
        com.example.youtube.proto.playlist.AddHistoryItemRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddHistoryItemMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse> updateSystemPlaylist(
        com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateSystemPlaylistMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ENSURE_DEFAULT_USER_PLAYLISTS = 0;
  private static final int METHODID_ADD_HISTORY_ITEM = 1;
  private static final int METHODID_UPDATE_SYSTEM_PLAYLIST = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ENSURE_DEFAULT_USER_PLAYLISTS:
          serviceImpl.ensureDefaultUserPlaylists((com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest) request,
              (io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse>) responseObserver);
          break;
        case METHODID_ADD_HISTORY_ITEM:
          serviceImpl.addHistoryItem((com.example.youtube.proto.playlist.AddHistoryItemRequest) request,
              (io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.AddHistoryItemResponse>) responseObserver);
          break;
        case METHODID_UPDATE_SYSTEM_PLAYLIST:
          serviceImpl.updateSystemPlaylist((com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest) request,
              (io.grpc.stub.StreamObserver<com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getEnsureDefaultUserPlaylistsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsRequest,
              com.example.youtube.proto.playlist.EnsureDefaultUserPlaylistsResponse>(
                service, METHODID_ENSURE_DEFAULT_USER_PLAYLISTS)))
        .addMethod(
          getAddHistoryItemMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.youtube.proto.playlist.AddHistoryItemRequest,
              com.example.youtube.proto.playlist.AddHistoryItemResponse>(
                service, METHODID_ADD_HISTORY_ITEM)))
        .addMethod(
          getUpdateSystemPlaylistMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.youtube.proto.playlist.UpdateSystemPlaylistRequest,
              com.example.youtube.proto.playlist.UpdateSystemPlaylistResponse>(
                service, METHODID_UPDATE_SYSTEM_PLAYLIST)))
        .build();
  }

  private static abstract class PlaylistInternalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PlaylistInternalServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.youtube.proto.playlist.PlaylistProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PlaylistInternalService");
    }
  }

  private static final class PlaylistInternalServiceFileDescriptorSupplier
      extends PlaylistInternalServiceBaseDescriptorSupplier {
    PlaylistInternalServiceFileDescriptorSupplier() {}
  }

  private static final class PlaylistInternalServiceMethodDescriptorSupplier
      extends PlaylistInternalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PlaylistInternalServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PlaylistInternalServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PlaylistInternalServiceFileDescriptorSupplier())
              .addMethod(getEnsureDefaultUserPlaylistsMethod())
              .addMethod(getAddHistoryItemMethod())
              .addMethod(getUpdateSystemPlaylistMethod())
              .build();
        }
      }
    }
    return result;
  }
}
