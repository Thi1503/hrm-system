package com.hrm.requestapprovalservice.dto.response.hr;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.RemoteWorkType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HrRemoteApprovalResponse {

    /* ===== THÔNG TIN ĐƠN ===== */
    private Long requestId;
    private LocalDate remoteDate;
    private RemoteWorkType workType;
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
