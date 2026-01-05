package com.hrm.payrollservice.dto.response;

import com.hrm.payrollservice.enums.PayrollComponentType;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollDetailResponse {
    private PayrollComponentType componentType;
    private BigDecimal amount;
    private String description;
}
