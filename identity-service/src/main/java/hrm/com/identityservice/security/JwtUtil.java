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
            Long employeeId,
            String employeeName,
            List<String> roles
    ) {
        return Jwts.builder()
                .setSubject(userId) // sub
                .claim("username", username)
                .claim("status", status)
                .claim("employeeId", employeeId)
                .claim("employeeName", employeeName)
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

    public String extractUserIdFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /* ===== EXTRACT EMPLOYEE INFO ===== */
    public Long extractEmployeeId(String token) {
        return getClaims(token).get("employeeId", Long.class);
    }

    public String extractEmployeeName(String token) {
        return getClaims(token).get("employeeName", String.class);
    }
}
