package com.hrm.attendanceservice.dto.response;
import com.hrm.attendanceservice.entity.AttendanceStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HrAttendanceByDateResponse {

    Long employeeId;
    LocalDate workDate;

    LocalDateTime checkInTime;
    LocalDateTime checkOutTime;

    Integer lateMinutes;
    Integer earlyMinutes;
    Integer workMinutes;

    AttendanceStatus status;
}
