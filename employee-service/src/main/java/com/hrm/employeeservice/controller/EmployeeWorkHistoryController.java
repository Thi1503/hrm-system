package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.response.EmployeeWorkHistoryResponse;
import com.hrm.employeeservice.service.EmployeeWorkHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "employee-work-history")
@RestController
@RequestMapping("/employee-work-history")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeWorkHistoryController {

    EmployeeWorkHistoryService historyService;

    @GetMapping("/list-by-employee/{employeeId}")
    BaseResponse<List<EmployeeWorkHistoryResponse>> listByEmployee(
            @PathVariable("employeeId") Long employeeId) {
        return BaseResponse.success(
                historyService.getByEmployee(employeeId)
        );
    }
}
