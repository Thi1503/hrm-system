package com.hrm.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public Claims parseClaims(String token) {

        String unsignedToken = token.substring(0, token.lastIndexOf('.') + 1);

        return Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(unsignedToken)
                .getBody();
    }
}
