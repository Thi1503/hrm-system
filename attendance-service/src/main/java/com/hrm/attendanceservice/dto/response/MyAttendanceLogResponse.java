package com.hrm.attendanceservice.dto.response;

import com.hrm.attendanceservice.entity.AttendanceCheckType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyAttendanceLogResponse {

    LocalDateTime checkTime;
    AttendanceCheckType checkType;
    Boolean validLocation;
    String locationName;
}
