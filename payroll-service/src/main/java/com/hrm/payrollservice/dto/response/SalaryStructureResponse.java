package com.hrm.payrollservice.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructureResponse {

    private Long employeeId;

    private BigDecimal baseSalary;
    private BigDecimal allowance;

    private BigDecimal otRate;
    private BigDecimal latePenaltyPerMin;
    private BigDecimal earlyPenaltyPerMin;

    private LocalDate effectiveFrom;
}
