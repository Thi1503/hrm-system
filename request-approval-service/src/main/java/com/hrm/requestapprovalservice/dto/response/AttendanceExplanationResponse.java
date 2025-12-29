package com.hrm.requestapprovalservice.dto.response;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.ExplanationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceExplanationResponse {

    Long id;
    Long employeeId;
    LocalDate workDate;
    ExplanationType explanationType;
    String reason;
    String attachmentUrl;
    ApprovalStatus status;
    LocalDateTime createdAt;
}
