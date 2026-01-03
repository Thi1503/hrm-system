package com.hrm.requestapprovalservice.kafka.event;

import com.hrm.requestapprovalservice.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceApprovalEvent {

    private RequestType requestType;   // EXPLANATION / LEAVE / OT / REMOTE
    private Long employeeId;

    private LocalDate workDate;         // EXPLANATION / OT / REMOTE
    private LocalDate fromDate;         // LEAVE
    private LocalDate toDate;

    private String explanationType;     // LATE / EARLY / ABSENT
}
