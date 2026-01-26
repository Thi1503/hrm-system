package com.hrm.employeeservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeMyInfoResponse {

    Long id;
    String fullName;
    String jobPosition;            // job title / role

    LocalDate dateOfBirth;
    String gender;

    String idNumber;         // CMND/CCCD
    String phone;
    String personalEmail;
    String address;

    // ===== WORK INFO =====
    String employeeCode;
    String department;
    String position;
    String seniority;        // "4 tháng, 20 ngày"
    String companyEmail;
    LocalDate joinDate;

    String avatarUrl;
}
