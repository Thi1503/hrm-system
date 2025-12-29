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
public class UpdateLeaveRequest {

    @NotNull
    LeaveType leaveType;

    @NotNull
    LocalDate fromDate;

    @NotNull
    LocalDate toDate;

    @NotNull
    BigDecimal totalDays;

    String reason;
    String attachmentUrl;
}
