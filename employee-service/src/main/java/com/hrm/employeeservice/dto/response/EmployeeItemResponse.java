package com.hrm.employeeservice.dto.response;

import com.hrm.employeeservice.entity.EmploymentStatus;
import com.hrm.employeeservice.entity.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeItemResponse {
    Long id;
    String accountId;
    String fullName;
    String code;
    Gender gender;
    LocalDate dateOfBirth;
    String departmentName;
    String positionName;
    EmploymentStatus employmentStatus;
}
