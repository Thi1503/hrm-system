package hrm.com.identityservice.security;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import hrm.com.identityservice.configuration.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtConfig jwtConfig;

    /* ===== CREATE TOKEN ===== */
    public String generateToken(
            String userId,
            String username,
            String status,
            List<String> roles
    ) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .claim("status", status)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtConfig.getExpirationMillis())
                )
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                .compact();
    }

    /* ===== PARSE TOKEN ===== */
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }

    public String getUsername(String token) {
        return getClaims(token).get("username", String.class);
    }

    public String extractUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject(); // sub = userId
    }
}
