package com.hrm.requestapprovalservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrExplanationApprovalResponse;
import com.hrm.requestapprovalservice.service.HrApprovalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
