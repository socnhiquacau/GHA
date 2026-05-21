package com.example.youtube.proto.video;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.0)",
    comments = "Source: video.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class VideoQueryServiceGrpc {

  private VideoQueryServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "youtube.video.VideoQueryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetVideoByIdRequest,
      com.example.youtube.proto.video.GetVideoByIdResponse> getGetVideoByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVideoById",
      requestType = com.example.youtube.proto.video.GetVideoByIdRequest.class,
      responseType = com.example.youtube.proto.video.GetVideoByIdResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetVideoByIdRequest,
      com.example.youtube.proto.video.GetVideoByIdResponse> getGetVideoByIdMethod() {
    io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetVideoByIdRequest, com.example.youtube.proto.video.GetVideoByIdResponse> getGetVideoByIdMethod;
    if ((getGetVideoByIdMethod = VideoQueryServiceGrpc.getGetVideoByIdMethod) == null) {
      synchronized (VideoQueryServiceGrpc.class) {
        if ((getGetVideoByIdMethod = VideoQueryServiceGrpc.getGetVideoByIdMethod) == null) {
          VideoQueryServiceGrpc.getGetVideoByIdMethod = getGetVideoByIdMethod =
              io.grpc.MethodDescriptor.<com.example.youtube.proto.video.GetVideoByIdRequest, com.example.youtube.proto.video.GetVideoByIdResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVideoById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.video.GetVideoByIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.video.GetVideoByIdResponse.getDefaultInstance()))
              .setSchemaDescriptor(new VideoQueryServiceMethodDescriptorSupplier("GetVideoById"))
              .build();
        }
      }
    }
    return getGetVideoByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetVideosByIdsRequest,
      com.example.youtube.proto.video.GetVideosByIdsResponse> getGetVideosByIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVideosByIds",
      requestType = com.example.youtube.proto.video.GetVideosByIdsRequest.class,
      responseType = com.example.youtube.proto.video.GetVideosByIdsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetVideosByIdsRequest,
      com.example.youtube.proto.video.GetVideosByIdsResponse> getGetVideosByIdsMethod() {
    io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetVideosByIdsRequest, com.example.youtube.proto.video.GetVideosByIdsResponse> getGetVideosByIdsMethod;
    if ((getGetVideosByIdsMethod = VideoQueryServiceGrpc.getGetVideosByIdsMethod) == null) {
      synchronized (VideoQueryServiceGrpc.class) {
        if ((getGetVideosByIdsMethod = VideoQueryServiceGrpc.getGetVideosByIdsMethod) == null) {
          VideoQueryServiceGrpc.getGetVideosByIdsMethod = getGetVideosByIdsMethod =
              io.grpc.MethodDescriptor.<com.example.youtube.proto.video.GetVideosByIdsRequest, com.example.youtube.proto.video.GetVideosByIdsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVideosByIds"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.video.GetVideosByIdsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.video.GetVideosByIdsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new VideoQueryServiceMethodDescriptorSupplier("GetVideosByIds"))
              .build();
        }
      }
    }
    return getGetVideosByIdsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetTrendingCandidatesRequest,
      com.example.youtube.proto.video.GetTrendingCandidatesResponse> getGetTrendingCandidatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTrendingCandidates",
      requestType = com.example.youtube.proto.video.GetTrendingCandidatesRequest.class,
      responseType = com.example.youtube.proto.video.GetTrendingCandidatesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetTrendingCandidatesRequest,
      com.example.youtube.proto.video.GetTrendingCandidatesResponse> getGetTrendingCandidatesMethod() {
    io.grpc.MethodDescriptor<com.example.youtube.proto.video.GetTrendingCandidatesRequest, com.example.youtube.proto.video.GetTrendingCandidatesResponse> getGetTrendingCandidatesMethod;
    if ((getGetTrendingCandidatesMethod = VideoQueryServiceGrpc.getGetTrendingCandidatesMethod) == null) {
      synchronized (VideoQueryServiceGrpc.class) {
        if ((getGetTrendingCandidatesMethod = VideoQueryServiceGrpc.getGetTrendingCandidatesMethod) == null) {
          VideoQueryServiceGrpc.getGetTrendingCandidatesMethod = getGetTrendingCandidatesMethod =
              io.grpc.MethodDescriptor.<com.example.youtube.proto.video.GetTrendingCandidatesRequest, com.example.youtube.proto.video.GetTrendingCandidatesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTrendingCandidates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.video.GetTrendingCandidatesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.youtube.proto.video.GetTrendingCandidatesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new VideoQueryServiceMethodDescriptorSupplier("GetTrendingCandidates"))
              .build();
        }
      }
    }
    return getGetTrendingCandidatesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VideoQueryServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VideoQueryServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VideoQueryServiceStub>() {
        @java.lang.Override
        public VideoQueryServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VideoQueryServiceStub(channel, callOptions);
        }
      };
    return VideoQueryServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VideoQueryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VideoQueryServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VideoQueryServiceBlockingStub>() {
        @java.lang.Override
        public VideoQueryServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VideoQueryServiceBlockingStub(channel, callOptions);
        }
      };
    return VideoQueryServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VideoQueryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VideoQueryServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VideoQueryServiceFutureStub>() {
        @java.lang.Override
        public VideoQueryServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VideoQueryServiceFutureStub(channel, callOptions);
        }
      };
    return VideoQueryServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getVideoById(com.example.youtube.proto.video.GetVideoByIdRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetVideoByIdResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetVideoByIdMethod(), responseObserver);
    }

    /**
     */
    default void getVideosByIds(com.example.youtube.proto.video.GetVideosByIdsRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetVideosByIdsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetVideosByIdsMethod(), responseObserver);
    }

    /**
     */
    default void getTrendingCandidates(com.example.youtube.proto.video.GetTrendingCandidatesRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetTrendingCandidatesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTrendingCandidatesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service VideoQueryService.
   */
  public static abstract class VideoQueryServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return VideoQueryServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service VideoQueryService.
   */
  public static final class VideoQueryServiceStub
      extends io.grpc.stub.AbstractAsyncStub<VideoQueryServiceStub> {
    private VideoQueryServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VideoQueryServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VideoQueryServiceStub(channel, callOptions);
    }

    /**
     */
    public void getVideoById(com.example.youtube.proto.video.GetVideoByIdRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetVideoByIdResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVideoByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getVideosByIds(com.example.youtube.proto.video.GetVideosByIdsRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetVideosByIdsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVideosByIdsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTrendingCandidates(com.example.youtube.proto.video.GetTrendingCandidatesRequest request,
        io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetTrendingCandidatesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTrendingCandidatesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service VideoQueryService.
   */
  public static final class VideoQueryServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<VideoQueryServiceBlockingStub> {
    private VideoQueryServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VideoQueryServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VideoQueryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.youtube.proto.video.GetVideoByIdResponse getVideoById(com.example.youtube.proto.video.GetVideoByIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVideoByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.youtube.proto.video.GetVideosByIdsResponse getVideosByIds(com.example.youtube.proto.video.GetVideosByIdsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVideosByIdsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.youtube.proto.video.GetTrendingCandidatesResponse getTrendingCandidates(com.example.youtube.proto.video.GetTrendingCandidatesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTrendingCandidatesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service VideoQueryService.
   */
  public static final class VideoQueryServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<VideoQueryServiceFutureStub> {
    private VideoQueryServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VideoQueryServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VideoQueryServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.youtube.proto.video.GetVideoByIdResponse> getVideoById(
        com.example.youtube.proto.video.GetVideoByIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVideoByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.youtube.proto.video.GetVideosByIdsResponse> getVideosByIds(
        com.example.youtube.proto.video.GetVideosByIdsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVideosByIdsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.youtube.proto.video.GetTrendingCandidatesResponse> getTrendingCandidates(
        com.example.youtube.proto.video.GetTrendingCandidatesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTrendingCandidatesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_VIDEO_BY_ID = 0;
  private static final int METHODID_GET_VIDEOS_BY_IDS = 1;
  private static final int METHODID_GET_TRENDING_CANDIDATES = 2;

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
        case METHODID_GET_VIDEO_BY_ID:
          serviceImpl.getVideoById((com.example.youtube.proto.video.GetVideoByIdRequest) request,
              (io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetVideoByIdResponse>) responseObserver);
          break;
        case METHODID_GET_VIDEOS_BY_IDS:
          serviceImpl.getVideosByIds((com.example.youtube.proto.video.GetVideosByIdsRequest) request,
              (io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetVideosByIdsResponse>) responseObserver);
          break;
        case METHODID_GET_TRENDING_CANDIDATES:
          serviceImpl.getTrendingCandidates((com.example.youtube.proto.video.GetTrendingCandidatesRequest) request,
              (io.grpc.stub.StreamObserver<com.example.youtube.proto.video.GetTrendingCandidatesResponse>) responseObserver);
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
          getGetVideoByIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.youtube.proto.video.GetVideoByIdRequest,
              com.example.youtube.proto.video.GetVideoByIdResponse>(
                service, METHODID_GET_VIDEO_BY_ID)))
        .addMethod(
          getGetVideosByIdsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.youtube.proto.video.GetVideosByIdsRequest,
              com.example.youtube.proto.video.GetVideosByIdsResponse>(
                service, METHODID_GET_VIDEOS_BY_IDS)))
        .addMethod(
          getGetTrendingCandidatesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.youtube.proto.video.GetTrendingCandidatesRequest,
              com.example.youtube.proto.video.GetTrendingCandidatesResponse>(
                service, METHODID_GET_TRENDING_CANDIDATES)))
        .build();
  }

  private static abstract class VideoQueryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VideoQueryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.youtube.proto.video.VideoProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VideoQueryService");
    }
  }

  private static final class VideoQueryServiceFileDescriptorSupplier
      extends VideoQueryServiceBaseDescriptorSupplier {
    VideoQueryServiceFileDescriptorSupplier() {}
  }

  private static final class VideoQueryServiceMethodDescriptorSupplier
      extends VideoQueryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    VideoQueryServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (VideoQueryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VideoQueryServiceFileDescriptorSupplier())
              .addMethod(getGetVideoByIdMethod())
              .addMethod(getGetVideosByIdsMethod())
              .addMethod(getGetTrendingCandidatesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
