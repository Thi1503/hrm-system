package hrm.com.identityservice.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {
    private String name;
    private String description;
}
