package com.example.youtube.common.context;

import com.example.youtube.common.exception.UnauthorizedException;

public final class RequestContextHolder {
    private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

    private RequestContextHolder() {
    }

    public static void set(RequestContext context) {
        CONTEXT.set(context);
    }

    public static RequestContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static Long requireUserId() {
        RequestContext context = get();
        if (context == null || context.getUserId() == null) {
            throw new UnauthorizedException("Missing authenticated user");
        }
        return context.getUserId();
    }
}
