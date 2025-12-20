package hrm.com.identityservice.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration-days}")
    private long expirationDays;

    public long getExpirationMillis() {
        return expirationDays * 24 * 60 * 60 * 1000;
    }
}
