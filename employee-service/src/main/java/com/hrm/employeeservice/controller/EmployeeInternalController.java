package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.internalRequest.AccountIdRequest;
import com.hrm.employeeservice.dto.response.internalResponse.EmployeeInfoResponse;
import com.hrm.employeeservice.dto.response.internalResponse.EmployeeInternalItemResponse;
import com.hrm.employeeservice.dto.response.internalResponse.EmployeeSimpleResponse;
import com.hrm.employeeservice.service.EmployeeInternalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "employees")
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeInternalController {

    EmployeeInternalService employeeInternalService;

    // dùng trong identity-service
    @PostMapping("/by-account-id")
    BaseResponse<EmployeeInfoResponse> getByAccountId(@RequestBody AccountIdRequest request){
        return BaseResponse.success(employeeInternalService.getByAccountId(request.getAccountId()));
    };

    // dùng trong request-approval-service khi ấy danh sách tôi đơn duyệt
    @GetMapping("/by-manager/{managerId}")
    public BaseResponse<List<EmployeeSimpleResponse>> getByManager(
            @PathVariable("managerId") Long managerId
    ) {
        return BaseResponse.success(
                employeeInternalService.getEmployeesByManager(managerId)
        );
    }

    @GetMapping("/by-hr/all-active-employee")
    public BaseResponse<List<EmployeeInternalItemResponse>> getAllActiveEmployee() {
        return BaseResponse.success(
                employeeInternalService.getAllActiveInternal()
        );
    }

    @GetMapping("/by-payroll/all-employees")
    public BaseResponse<List<EmployeeSimpleResponse>> getAllEmployeesForPayroll() {
        return BaseResponse.success(
                employeeInternalService.getAllEmployeesForPayroll()
        );
    }

}
