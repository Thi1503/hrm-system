package com.hrm.employeeservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeImportRequest {

    EmployeeCreateRequest employee;

    EmployeeContractCreateRequest contract;

    List<EmployeeRelationshipItemRequest> relationships;

    List<EmployeeEducationItemRequest> educations;
}
