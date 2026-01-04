package com.hrm.payrollservice.client;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.response.client.PayrollAttendanceByMonthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "attendance-service",
        url = "${attendance.service.url}"
)
public interface AttendanceClient {

    // internal – payroll-service dùng
    @GetMapping("/attendance/hr/internal/payroll/by-month")
    BaseResponse<List<PayrollAttendanceByMonthResponse>> getForPayrollByMonth(
            @RequestParam("month") String month
    );
}
