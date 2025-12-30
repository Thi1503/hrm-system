package com.hrm.requestapprovalservice.dto.response;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtRequestResponse {

    Long id;
    Long employeeId;
    LocalDate otDate;
    LocalTime startTime;
    LocalTime endTime;
    BigDecimal totalHours;
    String reason;
    ApprovalStatus status;
    LocalDateTime createdAt;
}
