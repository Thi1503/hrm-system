package com.hrm.payrollservice.dto.response.client;

import com.hrm.payrollservice.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovedRequestForPayrollResponse {

    /* ===== COMMON ===== */
    private RequestType requestType;
    private Long requestId;
    private Long employeeId;

    /* ===== EXPLANATION / OT / REMOTE ===== */
    private LocalDate workDate;

    /* ===== LEAVE ===== */
    private LocalDate fromDate;
    private LocalDate toDate;

    /* ===== OT ===== */
    private LocalTime startTime;
    private LocalTime endTime;

    /* ===== EXPLANATION ===== */
    private String explanationType;
}
