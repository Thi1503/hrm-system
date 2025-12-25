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
public class EmployeeEducationResponse {

    Long id;

    String degree;
    String major;
    String school;

    LocalDate startDate;
    LocalDate endDate;

    String description;
    LocalDateTime createdAt;
}
