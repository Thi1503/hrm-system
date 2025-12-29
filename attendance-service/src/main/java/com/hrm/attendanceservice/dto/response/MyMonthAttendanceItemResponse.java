package com.hrm.attendanceservice.dto.response;

import com.hrm.attendanceservice.entity.AttendanceStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyMonthAttendanceItemResponse {

    LocalDate workDate;
    AttendanceStatus status;
    Integer workMinutes;
    Integer lateMinutes;
    Integer earlyMinutes;
}
