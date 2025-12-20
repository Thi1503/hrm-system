package hrm.com.identityservice.dto.request;

import hrm.com.identityservice.entity.UserStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String username;
    private String password;
}
