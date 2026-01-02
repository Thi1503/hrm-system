package com.hrm.requestapprovalservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.response.ManagerExplanationApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.ManagerLeaveApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.ManagerOtApprovalResponse;
import com.hrm.requestapprovalservice.service.ManagerApprovalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "manager-approvals")
@RestController
@RequestMapping("/approvals/manager")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagerApprovalController {

    ManagerApprovalService managerApprovalService;

    @GetMapping("/explanations")
    public BaseResponse<List<ManagerExplanationApprovalResponse>> getExplanations(
            @RequestHeader("X-User-Id") Long managerId
    ) {
        return BaseResponse.success(
                managerApprovalService.getPendingExplanationsForManager(managerId)
        );
    }

    @GetMapping("/leaves")
    public BaseResponse<List<ManagerLeaveApprovalResponse>> getLeaves(
            @RequestHeader("X-User-Id") Long managerId
    ) {
        return BaseResponse.success(
                managerApprovalService.getPendingLeavesForManager(managerId)
        );
    }

    @GetMapping("/ots")
    public BaseResponse<List<ManagerOtApprovalResponse>> getOts(
            @RequestHeader("X-User-Id") Long managerId
    ) {
        return BaseResponse.success(
                managerApprovalService.getPendingOtsForManager(managerId)
        );
    }


}
