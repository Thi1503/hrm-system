package com.hrm.payrollservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.response.GenerateTimesheetResponse;
import com.hrm.payrollservice.service.PayrollTimesheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payroll/timesheet")
@RequiredArgsConstructor
public class PayrollTimesheetController {

    private final PayrollTimesheetService timesheetService;

    @PostMapping("/generate")
    public BaseResponse<GenerateTimesheetResponse> generate(
            @RequestParam String month
    ) {
        return BaseResponse.success(
                timesheetService.generateMonth(month)
        );
    }

    @PostMapping("/regenerate")
    public BaseResponse<GenerateTimesheetResponse> regenerate(
            @RequestParam String month
    ) {
        return BaseResponse.success(
                timesheetService.regenerateMonth(month)
        );
    }
}

