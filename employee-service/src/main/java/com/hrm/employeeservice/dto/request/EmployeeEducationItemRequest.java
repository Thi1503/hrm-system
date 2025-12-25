package com.hrm.employeeservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeEducationItemRequest {

    String degree;      // Cử nhân, Thạc sĩ, Tiến sĩ...
    String major;       // CNTT, Kinh tế...
    String school;      // Đại học Bách Khoa...
    LocalDate startDate;
    LocalDate endDate;
    String description;
}
