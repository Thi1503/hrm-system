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
public class EmployeeResponse {

    Long id;
    String accountId;
    String fullName;
    String code;

    Gender gender;
    LocalDate dateOfBirth;

    String phoneNumber;
    String email;

    Long departmentId;
    String departmentName;

    Long positionId;
    String positionName;

    Long managerId;
    String managerName;

    EmploymentStatus employmentStatus;

    BigDecimal salaryBasic;

    LocalDate startWorkDate;
    LocalDate endWorkDate;

    String avatarUrl;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
