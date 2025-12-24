package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.EmployeeContractCreateRequest;
import com.hrm.employeeservice.dto.request.EmployeeContractUpdateRequest;
import com.hrm.employeeservice.dto.response.EmployeeContractResponse;
import com.hrm.employeeservice.service.EmployeeContractService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "employee-contracts")
@RestController
@RequestMapping("/employee-contracts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeContractController {

    EmployeeContractService contractService;

    @PostMapping("/create")
    BaseResponse<EmployeeContractResponse> create(
            @RequestBody @Valid EmployeeContractCreateRequest request) {
        return BaseResponse.success(contractService.create(request));
    }

    @PostMapping("/update/{id}")
    BaseResponse<EmployeeContractResponse> update(
            @PathVariable("id")  Long id,
            @RequestBody @Valid EmployeeContractUpdateRequest request) {
        return BaseResponse.success(contractService.update(id, request));
    }

    @GetMapping("/detail/{id}")
    BaseResponse<EmployeeContractResponse> detail(@PathVariable("id") Long id) {
        return BaseResponse.success(contractService.getDetail(id));
    }

    @GetMapping("/list")
    BaseResponse<List<EmployeeContractResponse>> list() {
        return BaseResponse.success(contractService.getList());
    }

    @GetMapping("/list-by-employee/{employeeId}")
    BaseResponse<List<EmployeeContractResponse>> listByEmployee(
            @PathVariable("employeeId")  Long employeeId) {
        return BaseResponse.success(contractService.getByEmployee(employeeId));
    }

    @DeleteMapping("/delete/{id}")
    BaseResponse<Void> delete(@PathVariable("id") Long id) {
        contractService.delete(id);
        return BaseResponse.success(null);
    }
}
