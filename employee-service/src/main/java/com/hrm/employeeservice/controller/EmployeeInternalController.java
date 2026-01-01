package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.internalRequest.AccountIdRequest;
import com.hrm.employeeservice.dto.response.internalResponse.EmployeeInfoResponse;
import com.hrm.employeeservice.service.EmployeeInternalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
