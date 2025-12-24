package com.hrm.employeeservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRelationshipUpdateRequest {

    @NotEmpty
    List<EmployeeRelationshipItemRequest> relationships;
}
