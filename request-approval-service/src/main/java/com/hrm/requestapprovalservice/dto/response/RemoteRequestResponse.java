package com.hrm.requestapprovalservice.dto.response;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.RemoteWorkType;
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
public class RemoteRequestResponse {

    Long id;
    Long employeeId;
    LocalDate remoteDate;
    RemoteWorkType workType;
    String reason;
    ApprovalStatus status;
    LocalDateTime createdAt;
}

