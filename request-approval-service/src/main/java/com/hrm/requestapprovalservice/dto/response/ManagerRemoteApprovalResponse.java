package com.hrm.requestapprovalservice.dto.response;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.RemoteWorkType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerRemoteApprovalResponse {

    Long requestId;

    Long employeeId;
    String employeeName;

    LocalDate remoteDate;
    RemoteWorkType workType;

    String reason;

    ApprovalStatus status;
}
