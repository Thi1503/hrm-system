package com.hrm.requestapprovalservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.request.CreateLeaveRequest;
import com.hrm.requestapprovalservice.dto.request.UpdateLeaveRequest;
import com.hrm.requestapprovalservice.dto.response.LeaveRequestResponse;
import com.hrm.requestapprovalservice.service.LeaveRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "leave-requests")
@RestController
@RequestMapping("/leave-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveRequestController {

    LeaveRequestService service;

    @PostMapping("/create")
    BaseResponse<LeaveRequestResponse> create(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid CreateLeaveRequest request) {

        return BaseResponse.success(service.create(userId, request));
    }

    @PostMapping("/update/{id}")
    BaseResponse<LeaveRequestResponse> update(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid UpdateLeaveRequest request) {

        return BaseResponse.success(service.update(id, userId, request));
    }

    @PostMapping("/cancel/{id}")
    BaseResponse<Void> cancel(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") String userId) {

        service.cancel(id, userId);
        return BaseResponse.success(null);
    }

    @GetMapping("/detail/{id}")
    BaseResponse<LeaveRequestResponse> detail(
            @PathVariable("id") Long id) {

        return BaseResponse.success(service.detail(id));
    }

    @GetMapping("/list")
    BaseResponse<List<LeaveRequestResponse>> listMy(
            @RequestHeader("X-User-Id") String userId) {

        return BaseResponse.success(service.listMy(userId));
    }
}
