package com.hrm.requestapprovalservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.request.RejectApprovalRequest;
import com.hrm.requestapprovalservice.service.ApprovalRejectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApprovalRejectController {

    ApprovalRejectService rejectService;

    @PostMapping("/reject")
    public BaseResponse<Void> reject(
            @RequestHeader("X-User-Id") Long approverId,
            @RequestBody RejectApprovalRequest request) {

        rejectService.reject(approverId, request);
        return BaseResponse.success(null);
    }
}
