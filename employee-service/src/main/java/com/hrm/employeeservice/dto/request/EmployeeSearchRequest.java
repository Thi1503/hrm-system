package com.hrm.employeeservice.dto.request;

import com.hrm.employeeservice.entity.EmploymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeSearchRequest {

    String keyword;          // search theo code / fullName / phone / email
    Long departmentId;
    Long positionId;
    EmploymentStatus employmentStatus;
    Long managerId;

    Integer page;            // default = 0
    Integer size;            // default = 10
}
