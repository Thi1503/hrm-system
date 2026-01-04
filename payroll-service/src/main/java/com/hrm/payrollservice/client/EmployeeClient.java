package com.hrm.payrollservice.client;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.response.client.EmployeeSimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "employee-service",
        url = "${employee.service.url}"
)
public interface EmployeeClient {

    // internal – request-approval-service dùng
    @GetMapping("/internal/by-payroll/all-employees")
    BaseResponse<List<EmployeeSimpleResponse>> getAllEmployees(
    );

}
