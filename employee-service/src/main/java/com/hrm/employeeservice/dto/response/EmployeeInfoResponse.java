package com.hrm.employeeservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeInfoResponse {
    Long employeeId;
    String fullName;
    Long departmentId;
    String departmentName;
    String positionName;
}
