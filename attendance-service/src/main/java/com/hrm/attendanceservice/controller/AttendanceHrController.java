package com.hrm.attendanceservice.controller;

import com.hrm.attendanceservice.dto.request.AttendanceApproveLocationRequest;
import com.hrm.attendanceservice.dto.request.AttendanceManualAdjustRequest;
import com.hrm.attendanceservice.dto.response.HrAttendanceByDateResponse;
import com.hrm.attendanceservice.dto.response.HrAttendanceByMonthResponse;
import com.hrm.attendanceservice.service.AttendanceHrService;
import com.hrm.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "attendance-hr")
@RestController
@RequestMapping("/attendance/hr")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceHrController {

    AttendanceHrService service;

    @GetMapping("/by-date")
    BaseResponse<List<HrAttendanceByDateResponse>> byDate(
            @RequestParam LocalDate date,
            @RequestParam(required = false) Long employeeId
    ) {
        return BaseResponse.success(
                service.getByDate(date, employeeId)
        );
    }

    @GetMapping("/by-month")
    BaseResponse<List<HrAttendanceByMonthResponse>> byMonth(
            @RequestParam String month,
            @RequestParam(required = false) Long employeeId
    ) {
        return BaseResponse.success(
                service.getByMonth(month, employeeId)
        );
    }

    @PostMapping("/manual-adjust")
    BaseResponse<Void> manualAdjust(
            @RequestBody @Valid AttendanceManualAdjustRequest request
    ) {
        service.manualAdjust(request);
        return BaseResponse.success(null);
    }

    @PostMapping("/approve-invalid-location")
    BaseResponse<Void> approveInvalidLocation(
            @RequestBody AttendanceApproveLocationRequest request
    ) {
        service.approveInvalidLocation(request);
        return BaseResponse.success(null);
    }



}

