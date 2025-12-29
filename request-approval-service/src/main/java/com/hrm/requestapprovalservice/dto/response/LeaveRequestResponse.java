package com.hrm.requestapprovalservice.dto.response;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.LeaveType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveRequestResponse {

    Long id;
    Long employeeId;
    LeaveType leaveType;
    LocalDate fromDate;
    LocalDate toDate;
    BigDecimal totalDays;
    String reason;
    String attachmentUrl;
    ApprovalStatus status;
    LocalDateTime createdAt;
}
