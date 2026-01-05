package com.hrm.payrollservice.dto.response;

import com.hrm.payrollservice.enums.PayrollStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollResponse {

    private Long payrollId;
    private Long employeeId;
    private String month;

    private BigDecimal grossSalary;
    private BigDecimal totalDeduction;
    private BigDecimal netSalary;

    private PayrollStatus status;

    private List<PayrollDetailResponse> details;
}
