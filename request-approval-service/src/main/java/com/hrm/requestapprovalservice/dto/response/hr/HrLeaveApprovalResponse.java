package com.hrm.requestapprovalservice.dto.response.hr;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HrLeaveApprovalResponse {
    /* ===== THÔNG TIN ĐƠN ===== */
    private Long requestId;
    private LeaveType leaveType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private ApprovalStatus status;

    /* ===== THÔNG TIN NHÂN VIÊN ===== */
    private Long employeeId;
    private String employeeName;

    private Long managerId;
    private String managerName;

    private Long departmentId;
    private String departmentName;

    private Long positionId;
    private String positionName;
}
