package com.hrm.requestapprovalservice.controller.internal;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.response.internal.ApprovedRequestForPayrollResponse;
import com.hrm.requestapprovalservice.service.InternalPayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/internal/approvals")
@RequiredArgsConstructor
public class InternalApprovalController {

    private final InternalPayrollService payrollService;

    @GetMapping("/approved")
    public BaseResponse<List<ApprovedRequestForPayrollResponse>> getApprovedRequests(
            @RequestParam("month") String month   // format: yyyy-MM
    ) {
        return BaseResponse.success(
                payrollService.getApprovedRequestsByMonth(month)
        );
    }

}
