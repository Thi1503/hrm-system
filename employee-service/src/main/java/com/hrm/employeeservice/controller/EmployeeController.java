package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.EmployeeCreateRequest;
import com.hrm.employeeservice.dto.request.EmployeeUpdateRequest;
import com.hrm.employeeservice.dto.response.EmployeeItemResponse;
import com.hrm.employeeservice.dto.response.EmployeeResponse;
import com.hrm.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "employees")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {

    EmployeeService employeeService;

    @PostMapping("/create")
    BaseResponse<EmployeeResponse> create(
            @RequestBody @Valid EmployeeCreateRequest request) {
        return BaseResponse.success(employeeService.create(request));
    }

    @PostMapping("/update/{id}")
    BaseResponse<EmployeeResponse> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid EmployeeUpdateRequest request) {
        return BaseResponse.success(employeeService.update(id, request));
    }

    @GetMapping("/detail/{id}")
    BaseResponse<EmployeeResponse> detail(@PathVariable("id") Long id) {
        return BaseResponse.success(employeeService.getDetail(id));
    }

    @GetMapping("/list")
    BaseResponse<List<EmployeeItemResponse>> list() {
        return BaseResponse.success(employeeService.getList());
    }

    @DeleteMapping("/delete/{id}")
    BaseResponse<Void> delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return BaseResponse.success(null);
    }

    @GetMapping("/list-by-manager/{id}")
    BaseResponse<List<EmployeeItemResponse>> getListByManagerId(@PathVariable("id") Long id) {
        return BaseResponse.success(employeeService.getListByManagerId(id));
    }
}
