package com.hrm.employeeservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeFullResponse {

    EmployeeResponse employee;

    EmployeeContractResponse contract;

    List<EmployeeRelationshipResponse> relationships;

    List<EmployeeEducationResponse> educations;
}
