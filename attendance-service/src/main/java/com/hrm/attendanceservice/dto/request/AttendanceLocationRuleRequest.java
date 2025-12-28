package com.hrm.attendanceservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceLocationRuleRequest {

    @NotBlank
    String name;

    @NotNull
    BigDecimal latitude;

    @NotNull
    BigDecimal longitude;

    @NotNull
    Integer radiusMeter;

    @NotNull
    Boolean isActive;
}
