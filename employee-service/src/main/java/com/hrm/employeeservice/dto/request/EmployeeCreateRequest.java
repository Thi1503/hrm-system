package com.hrm.employeeservice.dto.request;

import com.hrm.employeeservice.entity.EmploymentStatus;
import com.hrm.employeeservice.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeCreateRequest {

    @NotBlank
    String accountId;

    @NotBlank
    String fullName;

    @NotBlank
    String code;

    Gender gender;

    LocalDate dateOfBirth;

    String citizenId;
    LocalDate issuedDate;
    String issuedPlace;

    String phoneNumber;
    String email;
    String address;

    @NotNull
    Long departmentId;

    @NotNull
    Long positionId;

    Long managerId;

    Long companyId;

    @NotNull
    LocalDate startWorkDate;

    EmploymentStatus employmentStatus;

    BigDecimal salaryBasic;

    String extraInfo;
    String avatarUrl;
}
