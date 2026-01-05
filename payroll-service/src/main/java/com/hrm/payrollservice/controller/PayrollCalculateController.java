package com.hrm.payrollservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.request.CalculatePayrollRequest;
import com.hrm.payrollservice.dto.response.PayrollResponse;
import com.hrm.payrollservice.service.PayrollCalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll")
@RequiredArgsConstructor
public class PayrollCalculateController {

    private final PayrollCalculateService calculateService;

    @PostMapping("/calculate")
    public BaseResponse<PayrollResponse> calculate(
            @RequestBody CalculatePayrollRequest request
    ) {
        return BaseResponse.success(
                calculateService.calculate(request)
        );
    }
}
