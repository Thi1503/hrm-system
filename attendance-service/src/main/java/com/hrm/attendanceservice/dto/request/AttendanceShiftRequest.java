package com.hrm.attendanceservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
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
