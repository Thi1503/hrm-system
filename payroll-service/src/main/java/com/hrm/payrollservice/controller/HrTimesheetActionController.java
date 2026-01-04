package com.hrm.payrollservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.request.CloseTimesheetRequest;
import com.hrm.payrollservice.service.CloseTimesheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll/timesheet/hr")
@RequiredArgsConstructor
public class HrTimesheetActionController {

    private final CloseTimesheetService closeService;

    @PostMapping("/close")
    public BaseResponse<Void> closeEmployeeTimesheet(
            @RequestBody CloseTimesheetRequest request
    ) {
        closeService.closeEmployeeMonth(request);
        return BaseResponse.success(null);
    }
}
