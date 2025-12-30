package com.hrm.requestapprovalservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.request.CreateOtRequest;
import com.hrm.requestapprovalservice.dto.request.UpdateOtRequest;
import com.hrm.requestapprovalservice.dto.response.OtRequestResponse;
import com.hrm.requestapprovalservice.service.OtRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ot-requests")
@RestController
@RequestMapping("/ot-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtRequestController {

    OtRequestService service;

    @PostMapping("/create")
    BaseResponse<OtRequestResponse> create(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid CreateOtRequest request) {

        return BaseResponse.success(service.create(userId, request));
    }

    @PostMapping("/update/{id}")
    BaseResponse<OtRequestResponse> update(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid UpdateOtRequest request) {

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
    BaseResponse<OtRequestResponse> detail(
            @PathVariable("id") Long id) {

        return BaseResponse.success(service.detail(id));
    }

    @GetMapping("/list")
    BaseResponse<List<OtRequestResponse>> listMy(
            @RequestHeader("X-User-Id") String userId) {

        return BaseResponse.success(service.listMy(userId));
    }
}
