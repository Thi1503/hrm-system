package com.hrm.employeeservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeWorkHistoryResponse {

    LocalDate fromDate;
    LocalDate toDate;

    String departmentName;
    String positionName;

    String description;

    LocalDateTime createdAt;
}
