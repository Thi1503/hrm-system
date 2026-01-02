package com.hrm.requestapprovalservice.dto.response.hr;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HrOtApprovalResponse {

    /* ===== THÔNG TIN ĐƠN ===== */
    private Long requestId;
    private LocalDate otDate;
    private LocalTime startTime;
    private LocalTime endTime;
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
