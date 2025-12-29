package com.hrm.attendanceservice.controller;

import com.hrm.attendanceservice.dto.request.AttendanceCheckInRequest;
import com.hrm.attendanceservice.dto.request.AttendanceCheckOutRequest;
import com.hrm.attendanceservice.dto.response.MyMonthAttendanceItemResponse;
import com.hrm.attendanceservice.dto.response.MyTodayAttendanceResponse;
import com.hrm.attendanceservice.service.AttendanceService;
import com.hrm.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "attendance")
@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceController {

    AttendanceService attendanceService;

    @PostMapping("/check-in")
    BaseResponse<Void> checkIn(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid AttendanceCheckInRequest request) {

        attendanceService.checkIn(userId, request);
        return BaseResponse.success(null);
    }

    @PostMapping("/check-out")
    BaseResponse<Void> checkOut(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid AttendanceCheckOutRequest request) {

        attendanceService.checkOut(userId, request);
        return BaseResponse.success(null);
    }

    @GetMapping("/my-today")
    BaseResponse<MyTodayAttendanceResponse> myToday(
            @RequestHeader("X-User-Id") String userId) {

        return BaseResponse.success(
                attendanceService.getMyToday(userId)
        );
    }

    @GetMapping("/my-month")
    BaseResponse<List<MyMonthAttendanceItemResponse>> myMonth(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam("month") String month) {

        return BaseResponse.success(
                attendanceService.getMyMonth(userId, month)
        );
    }


}
