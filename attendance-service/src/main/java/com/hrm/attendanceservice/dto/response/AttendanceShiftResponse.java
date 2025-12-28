package com.hrm.attendanceservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceShiftResponse {

    Long id;
    String name;
    LocalTime startTime;
    LocalTime endTime;
    Integer lateThresholdMin;
    Integer earlyThresholdMin;
    LocalDateTime createdAt;
}

