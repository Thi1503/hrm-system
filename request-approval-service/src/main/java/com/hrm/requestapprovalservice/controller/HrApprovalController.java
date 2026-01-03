package com.hrm.requestapprovalservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.request.ApproveApprovalRequest;
import com.hrm.requestapprovalservice.dto.response.hr.HrExplanationApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrLeaveApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrOtApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrRemoteApprovalResponse;
import com.hrm.requestapprovalservice.service.HrApprovalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "hr-approvals")
@RestController
@RequestMapping("/approvals/hr")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HrApprovalController {
     HrApprovalService hrApprovalService;

    @GetMapping("/explanations")
    public BaseResponse<List<HrExplanationApprovalResponse>> getExplanations() {
        return BaseResponse.success(
                hrApprovalService.getPendingExplanations()
        );
    }

    @GetMapping("/leaves")
    public BaseResponse<List<HrLeaveApprovalResponse>> getLeaves() {
        return BaseResponse.success(hrApprovalService.getPendingLeavesForHr());
    }

    @GetMapping("/ots")
    public BaseResponse<List<HrOtApprovalResponse>> getOts() {
        return BaseResponse.success(hrApprovalService.getPendingOtsForHr());
    }

    @GetMapping("/remotes")
    public BaseResponse<List<HrRemoteApprovalResponse>> getRemotes() {
        return BaseResponse.success(hrApprovalService.getPendingRemotesForHr());
    }

    @PostMapping("/approve")
    public BaseResponse<Void> approve(
            @RequestHeader("X-User-Id") Long hrId,
            @RequestBody ApproveApprovalRequest request) {

        hrApprovalService.approve(hrId, request);
        return BaseResponse.success(null);
    }

}
