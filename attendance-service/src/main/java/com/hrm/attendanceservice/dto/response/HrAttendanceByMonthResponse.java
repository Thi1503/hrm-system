package com.hrm.attendanceservice.dto.response;

import com.hrm.attendanceservice.entity.AttendanceStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HrAttendanceByMonthResponse {

    Long employeeId;
    LocalDate workDate;

    AttendanceStatus status;
    Integer workMinutes;
    Integer lateMinutes;
    Integer earlyMinutes;
}

