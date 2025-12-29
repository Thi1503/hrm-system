package com.hrm.attendanceservice.controller;

import com.hrm.attendanceservice.dto.response.HrAttendanceByDateResponse;
import com.hrm.attendanceservice.service.AttendanceHrService;
import com.hrm.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

