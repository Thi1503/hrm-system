package com.hrm.attendanceservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AttendanceShiftResponse {

    Long id;
    String name;
    LocalTime startTime;
    LocalTime endTime;
    Integer lateThresholdMin;
    Integer earlyThresholdMin;
    LocalDateTime createdAt;
}

