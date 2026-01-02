package com.hrm.employeeservice.dto.response.internalResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeInternalItemResponse {

    Long id;
    String fullName;

    Long managerId;
    String managerName;

    Long departmentId;
    String departmentName;

    Long positionId;
    String positionName;
}

