package hrm.com.identityservice.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionRequest {
    private String name;
    private String description;
}
