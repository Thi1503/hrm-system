package com.hrm.requestapprovalservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.requestapprovalservice.dto.request.CreateRemoteRequest;
import com.hrm.requestapprovalservice.dto.request.UpdateRemoteRequest;
import com.hrm.requestapprovalservice.dto.response.RemoteRequestResponse;
import com.hrm.requestapprovalservice.service.RemoteRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "remote-requests")
@RestController
@RequestMapping("/remote-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoteRequestController {

    RemoteRequestService service;

    @PostMapping("/create")
    BaseResponse<RemoteRequestResponse> create(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid CreateRemoteRequest request) {

        return BaseResponse.success(service.create(userId, request));
    }

    @PostMapping("/update/{id}")
    BaseResponse<RemoteRequestResponse> update(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid UpdateRemoteRequest request) {

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
    BaseResponse<RemoteRequestResponse> detail(
            @PathVariable("id") Long id) {

        return BaseResponse.success(service.detail(id));
    }

    @GetMapping("/list")
    BaseResponse<List<RemoteRequestResponse>> listMy(
            @RequestHeader("X-User-Id") String userId) {

        return BaseResponse.success(service.listMy(userId));
    }
}
