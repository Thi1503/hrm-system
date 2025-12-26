package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.EmployeeImportRequest;
import com.hrm.employeeservice.dto.response.EmployeeFullResponse;
import com.hrm.employeeservice.dto.response.EmployeeResponse;
import com.hrm.employeeservice.service.EmployeeImportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@Tag(name = "employee-import")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeImportController {

    EmployeeImportService importService;

    @PostMapping("/import")
    BaseResponse<EmployeeFullResponse> importEmployee(
            @RequestBody EmployeeImportRequest request) {
        return BaseResponse.success(
                importService.importAll(request)
        );
    }

}
