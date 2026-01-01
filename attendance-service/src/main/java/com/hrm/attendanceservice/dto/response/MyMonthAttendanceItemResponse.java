package com.hrm.attendanceservice.dto.response;

import com.hrm.attendanceservice.entity.AttendanceStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyMonthAttendanceItemResponse {

    LocalDate workDate;
    AttendanceStatus status;
    LocalDateTime checkInTime;
    LocalDateTime checkOutTime;
    Integer workMinutes;
    Integer lateMinutes;
    Integer earlyMinutes;
}
