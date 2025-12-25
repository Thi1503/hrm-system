package hrm.com.identityservice.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeInfoResponse {

    private Long employeeId;
    private String fullName;
    private Long departmentId;
    private String departmentName;
    private String positionName;
}
