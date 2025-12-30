package com.hrm.requestapprovalservice.dto.request;

import com.hrm.requestapprovalservice.enums.LeaveType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateLeaveRequest {

    @NotNull
    LeaveType leaveType;

    @NotNull
    LocalDate fromDate;

    @NotNull
    LocalDate toDate;

    String reason;
    String attachmentUrl;
}
