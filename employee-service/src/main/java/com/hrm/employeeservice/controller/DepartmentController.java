package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.DepartmentRequest;
import com.hrm.employeeservice.dto.response.DepartmentResponse;
import com.hrm.employeeservice.service.DepartmentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "departments")
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentController {
    DepartmentService departmentService;

    @PostMapping("/create")
    BaseResponse<DepartmentResponse> createDepartment(@RequestBody DepartmentRequest request) {
        return BaseResponse.success(departmentService.createDepartment(request));
    }
}
