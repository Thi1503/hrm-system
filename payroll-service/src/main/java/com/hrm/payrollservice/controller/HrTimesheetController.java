package com.hrm.payrollservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.response.MyMonthTimesheetResponse;
import com.hrm.payrollservice.service.HrTimesheetQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll/timesheet/hr")
@RequiredArgsConstructor
public class HrTimesheetController {

    private final HrTimesheetQueryService queryService;

    @GetMapping("/employee-month")
    public BaseResponse<MyMonthTimesheetResponse> employeeMonth(
            @RequestParam Long employeeId,
            @RequestParam String month
    ) {
        return BaseResponse.success(
                queryService.getEmployeeMonth(employeeId, month)
        );
    }
}
