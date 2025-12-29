package com.hrm.attendanceservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceManualAdjustRequest {

    Long employeeId;
    LocalDate workDate;

    LocalDateTime checkInTime;
    LocalDateTime checkOutTime;

    String reason;
}

