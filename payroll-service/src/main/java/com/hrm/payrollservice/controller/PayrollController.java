package com.hrm.payrollservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.request.CalculatePayrollRequest;
import com.hrm.payrollservice.dto.response.PayrollResponse;
import com.hrm.payrollservice.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll/hr")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping("/calculate")
    public BaseResponse<PayrollResponse> calculatePayroll(
            @RequestBody CalculatePayrollRequest request
    ) {
        return BaseResponse.success(
                payrollService.calculate(request)
        );
    }

    @PostMapping("/regenerate")
    public BaseResponse<PayrollResponse> regeneratePayroll(
            @RequestBody CalculatePayrollRequest request
    ) {
        return BaseResponse.success(
                payrollService.regenerate(request)
        );
    }

    @PostMapping("/approve")
    public BaseResponse<Void> approvePayroll(
            @RequestParam Long payrollId
    ) {
        payrollService.approve(payrollId);
        return BaseResponse.success(null);
    }

    @PostMapping("/pay")
    public BaseResponse<Void> payPayroll(
            @RequestParam Long payrollId
    ) {
        payrollService.pay(payrollId);
        return BaseResponse.success(null);
    }

}
