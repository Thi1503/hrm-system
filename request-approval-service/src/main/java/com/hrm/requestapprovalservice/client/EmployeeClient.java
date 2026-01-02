package com.hrm.requestapprovalservice.client;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.request.EmployeeSimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "employee-service",
        url = "${employee.service.url}"
)
public interface EmployeeClient {

    // internal – request-approval-service dùng
    @GetMapping("/internal/by-manager/{managerId}")
    BaseResponse<List<EmployeeSimpleResponse>> getByManager(
            @PathVariable("managerId") Long managerId
    );
}
