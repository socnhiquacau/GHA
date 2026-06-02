package com.example.youtube.gateway.api;

import com.example.youtube.common.header.HeaderConstants;
import com.example.youtube.gateway.auth.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Enumeration;

@Service
public class ApiProxyService {
    private final RestTemplate restTemplate;
    private final ApiRouteResolver routeResolver;

    public ApiProxyService(RestTemplate restTemplate, ApiRouteResolver routeResolver) {
        this.restTemplate = restTemplate;
        this.routeResolver = routeResolver;
    }

    public ResponseEntity<byte[]> forward(String requestPath,
                                          String queryString,
                                          String method,
                                          HttpHeaders inboundHeaders,
                                          byte[] body,
                                          AuthenticatedUser authenticatedUser) {
        String baseUrl = routeResolver.resolveBaseUrl(requestPath);
        String targetUrl = buildTargetUrl(baseUrl, requestPath, queryString);
        HttpMethod httpMethod = HttpMethod.valueOf(method);

        HttpHeaders outboundHeaders = filterHeaders(inboundHeaders);
        outboundHeaders.set(HeaderConstants.USER_ID, String.valueOf(authenticatedUser.userId()));
        outboundHeaders.set(HeaderConstants.USER_ROLE, authenticatedUser.role());
        outboundHeaders.set(HeaderConstants.REQUEST_ID, authenticatedUser.requestId());

        HttpEntity<byte[]> entity = (body != null && body.length > 0)
                ? new HttpEntity<>(body, outboundHeaders)
                : new HttpEntity<>(outboundHeaders);
        ResponseEntity<byte[]> downstreamResponse = restTemplate.exchange(targetUrl, httpMethod, entity, byte[].class);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.putAll(downstreamResponse.getHeaders());
        responseHeaders.set(HeaderConstants.REQUEST_ID, authenticatedUser.requestId());
        return ResponseEntity.status(downstreamResponse.getStatusCode())
                .headers(responseHeaders)
                .body(downstreamResponse.getBody());
    }

    public HttpHeaders extractHeaders(Enumeration<String> headerNames, HeaderValueResolver headerValueResolver) {
        HttpHeaders headers = new HttpHeaders();
        for (String headerName : Collections.list(headerNames)) {
            for (String value : Collections.list(headerValueResolver.resolve(headerName))) {
                headers.add(headerName, value);
            }
        }
        return headers;
    }

    private String buildTargetUrl(String baseUrl, String path, String queryString) {
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        StringBuilder result = new StringBuilder(normalizedBaseUrl).append(path);
        if (StringUtils.hasText(queryString)) {
            result.append('?').append(queryString);
        }
        return result.toString();
    }

    private HttpHeaders filterHeaders(HttpHeaders inboundHeaders) {
        HttpHeaders outbound = new HttpHeaders();
        inboundHeaders.forEach((name, values) -> {
            if (isHopByHopHeader(name)) {
                return;
            }
            if (HeaderConstants.USER_ID.equalsIgnoreCase(name)
                    || HeaderConstants.USER_ROLE.equalsIgnoreCase(name)
                    || HeaderConstants.REQUEST_ID.equalsIgnoreCase(name)
                    || HttpHeaders.COOKIE.equalsIgnoreCase(name)) {
                return;
            }
            outbound.put(name, values);
        });
        return outbound;
    }

    private boolean isHopByHopHeader(String name) {
        return "host".equalsIgnoreCase(name)
                || "connection".equalsIgnoreCase(name)
                || "keep-alive".equalsIgnoreCase(name)
                || "proxy-authenticate".equalsIgnoreCase(name)
                || "proxy-authorization".equalsIgnoreCase(name)
                || "te".equalsIgnoreCase(name)
                || "trailer".equalsIgnoreCase(name)
                || "transfer-encoding".equalsIgnoreCase(name)
                || "upgrade".equalsIgnoreCase(name)
                || "content-length".equalsIgnoreCase(name);
    }

    @FunctionalInterface
    public interface HeaderValueResolver {
        Enumeration<String> resolve(String headerName);
    }
}
