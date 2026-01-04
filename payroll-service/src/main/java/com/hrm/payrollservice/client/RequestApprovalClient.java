package com.hrm.payrollservice.client;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.response.client.ApprovedRequestForPayrollResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "request-approval-service",
        url = "${request-approval.service.url}"
)
public interface RequestApprovalClient {

    // internal – payroll-service dùng
    @GetMapping("/internal/approvals/approved")
    BaseResponse<List<ApprovedRequestForPayrollResponse>> getApprovedRequestsByMonth(
            @RequestParam("month") String month
    );
}
