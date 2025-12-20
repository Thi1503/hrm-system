package hrm.com.identityservice.dto.response;

import hrm.com.identityservice.entity.UserStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private UserStatus status;
}
