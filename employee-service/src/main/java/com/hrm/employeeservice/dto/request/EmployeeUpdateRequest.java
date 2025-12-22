package com.hrm.employeeservice.dto.request;

import com.hrm.employeeservice.entity.EmploymentStatus;
import com.hrm.employeeservice.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeUpdateRequest {

    @NotBlank
    String fullName;

    Gender gender;
    LocalDate dateOfBirth;

    String citizenId;
    LocalDate issuedDate;
    String issuedPlace;

    String phoneNumber;
    String email;
    String address;

    Long departmentId;
    Long positionId;
    Long managerId;

    LocalDate endWorkDate;

    EmploymentStatus employmentStatus;

    BigDecimal salaryBasic;

    String extraInfo;
    String avatarUrl;
}
