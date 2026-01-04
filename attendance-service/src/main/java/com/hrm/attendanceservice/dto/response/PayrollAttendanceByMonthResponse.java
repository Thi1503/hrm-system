package com.hrm.attendanceservice.dto.response;

import com.hrm.attendanceservice.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollAttendanceByMonthResponse {

    private Long employeeId;
    private LocalDate workDate;

    private AttendanceStatus status;

    private Integer workMinutes;
    private Integer lateMinutes;
    private Integer earlyMinutes;
}

