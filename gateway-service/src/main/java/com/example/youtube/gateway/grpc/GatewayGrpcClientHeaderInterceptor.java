package com.example.youtube.gateway.grpc;

import com.example.youtube.common.header.HeaderConstants;
import io.grpc.*;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@GrpcGlobalClientInterceptor
public class GatewayGrpcClientHeaderInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> USER_ID_HEADER =
            Metadata.Key.of(HeaderConstants.USER_ID.toLowerCase(), Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> USER_ROLE_HEADER =
            Metadata.Key.of(HeaderConstants.USER_ROLE.toLowerCase(), Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> REQUEST_ID_HEADER =
            Metadata.Key.of(HeaderConstants.REQUEST_ID.toLowerCase(), Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                                CallOptions callOptions,
                                                                Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                Long userId = GatewayGrpcContext.USER_ID.get();
                String userRole = GatewayGrpcContext.USER_ROLE.get();
                String requestId = GatewayGrpcContext.REQUEST_ID.get();
                if (userId != null) {
                    headers.put(USER_ID_HEADER, String.valueOf(userId));
                }
                if (StringUtils.hasText(userRole)) {
                    headers.put(USER_ROLE_HEADER, userRole);
                }
                if (StringUtils.hasText(requestId)) {
                    headers.put(REQUEST_ID_HEADER, requestId);
                }
                super.start(responseListener, headers);
            }
        };
    }
}
