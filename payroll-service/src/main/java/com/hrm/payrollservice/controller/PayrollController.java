package com.hrm.payrollservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.request.CalculatePayrollRequest;
import com.hrm.payrollservice.dto.response.PayrollResponse;
import com.hrm.payrollservice.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping("/hr/calculate")
    public BaseResponse<PayrollResponse> calculatePayroll(
            @RequestBody CalculatePayrollRequest request
    ) {
        return BaseResponse.success(
                payrollService.calculate(request)
        );
    }

    @PostMapping("/hr/regenerate")
    public BaseResponse<PayrollResponse> regeneratePayroll(
            @RequestBody CalculatePayrollRequest request
    ) {
        return BaseResponse.success(
                payrollService.regenerate(request)
        );
    }

    @PostMapping("/hr/approve")
    public BaseResponse<Void> approvePayroll(
            @RequestParam Long payrollId
    ) {
        payrollService.approve(payrollId);
        return BaseResponse.success(null);
    }

    @PostMapping("/hr/pay")
    public BaseResponse<Void> payPayroll(
            @RequestParam Long payrollId
    ) {
        payrollService.pay(payrollId);
        return BaseResponse.success(null);
    }

    @GetMapping("/my/month")
    public BaseResponse<PayrollResponse> myPayroll(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam String month
    ) {
        Long employeeId = Long.valueOf(userId);
        return BaseResponse.success(
                payrollService.getMyPayroll(employeeId, month)
        );
    }


}
