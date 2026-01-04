package com.hrm.payrollservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.response.MyMonthTimesheetResponse;
import com.hrm.payrollservice.service.MyTimesheetQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll/timesheet")
@RequiredArgsConstructor
public class MyTimesheetController {

    private final MyTimesheetQueryService queryService;

    @GetMapping("/my-month")
    public BaseResponse<MyMonthTimesheetResponse> myMonth(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam("month") String month
    ) {
        Long employeeId = Long.valueOf(userId);
        return BaseResponse.success(
                queryService.getMyMonth(employeeId, month)
        );
    }
}
