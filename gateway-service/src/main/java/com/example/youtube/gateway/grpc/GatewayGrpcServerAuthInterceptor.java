package com.example.youtube.gateway.grpc;

import com.example.youtube.gateway.auth.AuthenticatedUser;
import com.example.youtube.gateway.auth.CookieAuthenticationService;
import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@GrpcGlobalServerInterceptor
public class GatewayGrpcServerAuthInterceptor implements ServerInterceptor {
    private final CookieAuthenticationService cookieAuthenticationService;

    public GatewayGrpcServerAuthInterceptor(CookieAuthenticationService cookieAuthenticationService) {
        this.cookieAuthenticationService = cookieAuthenticationService;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                  Metadata headers,
                                                                  ServerCallHandler<ReqT, RespT> next) {
        try {
            AuthenticatedUser authenticatedUser = cookieAuthenticationService.authenticateGrpcCookie(headers);
            Context context = Context.current()
                    .withValue(GatewayGrpcContext.USER_ID, authenticatedUser.userId())
                    .withValue(GatewayGrpcContext.USER_ROLE, authenticatedUser.role())
                    .withValue(GatewayGrpcContext.REQUEST_ID, authenticatedUser.requestId());
            return Contexts.interceptCall(context, call, headers, next);
        } catch (RuntimeException ex) {
            call.close(Status.UNAUTHENTICATED.withDescription(ex.getMessage()), new Metadata());
            return new ServerCall.Listener<>() {
            };
        }
    }
}
