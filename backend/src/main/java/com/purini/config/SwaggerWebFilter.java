package com.purini.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class SwaggerWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().pathWithinApplication().value();

        if (path.startsWith("/api/swagger-ui") || path.startsWith("/api/webjars")
                || path.startsWith("/api/api-docs") || path.startsWith("/api/configuration")
                || path.startsWith("/api/swagger-resources") || path.startsWith("/api/v2/api-docs")) {
            exchange = exchange.mutate().request(request.mutate().path(path.substring(4)).build()).build();
        }
        return chain.filter(exchange);
    }
}
