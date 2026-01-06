package com.hrm.apigateway.filter;

import com.hrm.apigateway.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtHeaderPropagationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // Không có token → cho qua
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        try {
            String token = authHeader.substring(7);

            Claims claims = jwtUtil.parseClaims(token);

            // ⚠️ GIẢ ĐỊNH token có claim "userId"
            String userId = claims.get("userId", String.class);

            if (userId != null) {
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(builder ->
                                builder.header("X-User-Id", userId)
                        )
                        .build();

                return chain.filter(mutatedExchange);
            }

        } catch (Exception ex) {
            // Token sai → KHÔNG chặn (tạm thời)
            return chain.filter(exchange);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // chạy rất sớm
    }
}
