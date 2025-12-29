package com.hrm.requestapprovalservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.request.CreateAttendanceExplanationRequest;
import com.hrm.requestapprovalservice.dto.request.UpdateAttendanceExplanationRequest;
import com.hrm.requestapprovalservice.dto.response.AttendanceExplanationResponse;
import com.hrm.requestapprovalservice.service.AttendanceExplanationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "attendance-explanations")
@RestController
@RequestMapping("/attendance-explanations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceExplanationController {

    AttendanceExplanationService service;

    @PostMapping("/create")
    BaseResponse<AttendanceExplanationResponse> create(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid CreateAttendanceExplanationRequest request) {

        return BaseResponse.success(service.create(userId, request));
    }

    @PostMapping("/update/{id}")
    BaseResponse<AttendanceExplanationResponse> update(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid UpdateAttendanceExplanationRequest request) {

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
    BaseResponse<AttendanceExplanationResponse> detail(
            @PathVariable("id") Long id) {

        return BaseResponse.success(service.detail(id));
    }

    @GetMapping("/list")
    BaseResponse<List<AttendanceExplanationResponse>> listMy(
            @RequestHeader("X-User-Id") String userId) {

        return BaseResponse.success(service.listMy(userId));
    }
}
