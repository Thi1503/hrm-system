package com.hrm.requestapprovalservice.dto.response.manager;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.ExplanationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerExplanationApprovalResponse {

    Long requestId;

    Long employeeId;
    String employeeName;

    LocalDate workDate;
    ExplanationType explanationType;
    String reason;
    ApprovalStatus status;
}
