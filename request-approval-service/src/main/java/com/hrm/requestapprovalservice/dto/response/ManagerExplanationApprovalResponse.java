package com.hrm.requestapprovalservice.dto.response;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.ExplanationType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerExplanationApprovalResponse {

    private Long requestId;

    private Long employeeId;
    private String employeeName;

    private LocalDate workDate;
    private ExplanationType explanationType;
    private String reason;
    private ApprovalStatus status;
}
