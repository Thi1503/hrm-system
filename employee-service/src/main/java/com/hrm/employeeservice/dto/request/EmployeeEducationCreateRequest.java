package com.hrm.employeeservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeEducationCreateRequest {

    @NotNull
    Long employeeId;

    @NotEmpty
    List<EmployeeEducationItemRequest> educations;
}
