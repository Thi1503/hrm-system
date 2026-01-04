package com.hrm.payrollservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateSalaryStructureRequest {

    private Long employeeId;

    private BigDecimal baseSalary;
    private BigDecimal allowance;

    private BigDecimal otRate;

    private BigDecimal latePenaltyPerMin;
    private BigDecimal earlyPenaltyPerMin;

    private LocalDate effectiveFrom;
}
