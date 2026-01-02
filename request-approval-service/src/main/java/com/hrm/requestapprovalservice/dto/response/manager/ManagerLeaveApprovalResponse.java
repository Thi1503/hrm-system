package com.hrm.requestapprovalservice.dto.response.manager;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.LeaveType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerLeaveApprovalResponse {

    Long requestId;

    Long employeeId;
    String employeeName;

    LeaveType leaveType;
    LocalDate fromDate;
    LocalDate toDate;

    String reason;

    ApprovalStatus status;
}
