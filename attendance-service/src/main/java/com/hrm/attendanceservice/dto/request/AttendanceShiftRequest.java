package com.hrm.attendanceservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class AttendanceShiftRequest {

    @NotBlank
    String name;

    @NotNull
    LocalTime startTime;

    @NotNull
    LocalTime endTime;

    @NotNull
    Integer lateThresholdMin;

    @NotNull
    Integer earlyThresholdMin;
}
