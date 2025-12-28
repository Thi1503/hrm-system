package com.hrm.attendanceservice.controller;

import com.hrm.attendanceservice.dto.request.AttendanceLocationRuleRequest;
import com.hrm.attendanceservice.dto.response.AttendanceLocationRuleResponse;
import com.hrm.attendanceservice.service.AttendanceLocationRuleService;
import com.hrm.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "attendance-location-rules")
@RestController
@RequestMapping("/attendance-location-rules")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceLocationRuleController {

    AttendanceLocationRuleService service;

    @PostMapping("/create")
    BaseResponse<AttendanceLocationRuleResponse> create(
            @RequestBody @Valid AttendanceLocationRuleRequest request) {
        return BaseResponse.success(service.create(request));
    }

    @PostMapping("/update/{id}")
    BaseResponse<AttendanceLocationRuleResponse> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid AttendanceLocationRuleRequest request) {
        return BaseResponse.success(service.update(id, request));
    }

    @GetMapping("/detail/{id}")
    BaseResponse<AttendanceLocationRuleResponse> detail(
            @PathVariable("id") Long id) {
        return BaseResponse.success(service.getDetail(id));
    }

    @GetMapping("/list")
    BaseResponse<List<AttendanceLocationRuleResponse>> list() {
        return BaseResponse.success(service.getList());
    }

    @DeleteMapping("/delete/{id}")
    BaseResponse<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return BaseResponse.success(null);
    }
}
