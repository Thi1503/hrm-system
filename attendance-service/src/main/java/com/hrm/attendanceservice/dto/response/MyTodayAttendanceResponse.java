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
public class MyTodayAttendanceResponse {

    LocalDate workDate;
    LocalDateTime checkInTime;
    LocalDateTime checkOutTime;
    AttendanceStatus status;
    Integer workMinutes;
}
