package com.hrm.requestapprovalservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOtRequest {

    @NotNull
    LocalTime startTime;

    @NotNull
    LocalTime endTime;

    String reason;
}
