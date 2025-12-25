package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.EmployeeEducationCreateRequest;
import com.hrm.employeeservice.dto.request.EmployeeEducationUpdateRequest;
import com.hrm.employeeservice.dto.response.EmployeeEducationResponse;
import com.hrm.employeeservice.service.EmployeeEducationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "employee-educations")
@RestController
@RequestMapping("/employee-educations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeEducationController {

    EmployeeEducationService educationService;

    /** CREATE LIST */
    @PostMapping("/create")
    BaseResponse<List<EmployeeEducationResponse>> create(
            @RequestBody @Valid EmployeeEducationCreateRequest request) {
        return BaseResponse.success(educationService.create(request));
    }

    /** UPDATE LIST (BY EMPLOYEE) */
    @PostMapping("/update/{employeeId}")
    BaseResponse<List<EmployeeEducationResponse>> update(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody @Valid EmployeeEducationUpdateRequest request) {
        return BaseResponse.success(
                educationService.update(employeeId, request)
        );
    }

    /** GET LIST BY EMPLOYEE */
    @GetMapping("/list-by-employee/{employeeId}")
    BaseResponse<List<EmployeeEducationResponse>> listByEmployee(
            @PathVariable("employeeId") Long employeeId) {
        return BaseResponse.success(
                educationService.getByEmployee(employeeId)
        );
    }

    /** DELETE ALL */
    @DeleteMapping("/delete-by-employee/{employeeId}")
    BaseResponse<Void> deleteByEmployee(@PathVariable("employeeId") Long employeeId) {
        educationService.deleteByEmployee(employeeId);
        return BaseResponse.success(null);
    }
}
