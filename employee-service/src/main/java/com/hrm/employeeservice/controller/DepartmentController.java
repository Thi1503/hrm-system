package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.DepartmentRequest;
import com.hrm.employeeservice.dto.response.DepartmentResponse;
import com.hrm.employeeservice.service.DepartmentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    /** UPDATE */
    @PostMapping("/update/{id}")
    BaseResponse<DepartmentResponse> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid DepartmentRequest request) {
        return BaseResponse.success(departmentService.updateDepartment(id, request));
    }

    /** GET DETAIL */
    @GetMapping ("/detail/{id}")
    BaseResponse<DepartmentResponse> getDetail(@PathVariable("id") Long id) {
        return BaseResponse.success(departmentService.getDepartment(id));
    }

    /** GET LIST */
    @GetMapping("/list")
    BaseResponse<List<DepartmentResponse>> getList() {
        return BaseResponse.success(departmentService.getDepartments());
    }

    /** DELETE */
    @DeleteMapping("/delete/{id}")
    BaseResponse<Void> delete(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return BaseResponse.success(null);
    }

}
