package com.example.youtube.common.context;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestContext {
    private Long userId;
    private String userRole;
    private String requestId;
}
