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

        System.out.println("🔥 [GATEWAY] FILTER HIT");
        System.out.println("🔥 [GATEWAY] PATH = " + exchange.getRequest().getURI());

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println("🔥 [GATEWAY] AUTH = " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        try {
            Claims claims = jwtUtil.parseClaims(authHeader.substring(7));
            System.out.println("🔥 [GATEWAY] CLAIMS = " + claims);

            Number emp = claims.get("employeeId", Number.class);
            Long employeeId = emp != null ? emp.longValue() : null;

            System.out.println("🔥 [GATEWAY] EMPLOYEE_ID = " + employeeId);

            if (employeeId != null) {
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(builder -> {
                            builder.headers(h -> h.remove("X-User-Id"));
                            builder.header("X-User-Id", employeeId.toString());
                        })
                        .build();

                System.out.println("🔥 [GATEWAY] ADD HEADER X-User-Id=" + employeeId);
                return chain.filter(mutatedExchange);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return chain.filter(exchange);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}


