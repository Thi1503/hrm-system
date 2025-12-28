package com.hrm.attendanceservice.controller;

import com.hrm.attendanceservice.dto.request.AttendanceShiftRequest;
import com.hrm.attendanceservice.dto.response.AttendanceShiftResponse;
import com.hrm.attendanceservice.service.AttendanceShiftService;
import com.hrm.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "attendance-shifts")
@RestController
@RequestMapping("/attendance-shifts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceShiftController {

    AttendanceShiftService service;

    @PostMapping("/create")
    BaseResponse<AttendanceShiftResponse> create(
            @RequestBody @Valid AttendanceShiftRequest request) {
        return BaseResponse.success(service.create(request));
    }

    @PostMapping("/update/{id}")
    BaseResponse<AttendanceShiftResponse> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid AttendanceShiftRequest request) {
        return BaseResponse.success(service.update(id, request));
    }

    @GetMapping("/detail/{id}")
    BaseResponse<AttendanceShiftResponse> detail(
            @PathVariable("id") Long id) {
        return BaseResponse.success(service.getDetail(id));
    }

    @GetMapping("/list")
    BaseResponse<List<AttendanceShiftResponse>> list() {
        return BaseResponse.success(service.getList());
    }

    @DeleteMapping("/delete/{id}")
    BaseResponse<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return BaseResponse.success(null);
    }
}
