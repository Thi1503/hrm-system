package com.hrm.payrollservice.dto.request;

import lombok.Data;

@Data
public class CalculatePayrollRequest {
    private Long employeeId;
    private String month; // YYYY-MM
}
