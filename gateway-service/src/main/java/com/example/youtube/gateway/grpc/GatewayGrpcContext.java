package com.example.youtube.gateway.grpc;

import io.grpc.Context;

public final class GatewayGrpcContext {
    public static final Context.Key<Long> USER_ID = Context.key("gateway.user-id");
    public static final Context.Key<String> USER_ROLE = Context.key("gateway.user-role");
    public static final Context.Key<String> REQUEST_ID = Context.key("gateway.request-id");

    private GatewayGrpcContext() {
    }
}
