package com.hrm.requestapprovalservice.dto.response.manager;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerOtApprovalResponse {

    Long requestId;

    Long employeeId;
    String employeeName;

    LocalDate otDate;
    LocalTime startTime;
    LocalTime endTime;

    String reason;

    ApprovalStatus status;
}
