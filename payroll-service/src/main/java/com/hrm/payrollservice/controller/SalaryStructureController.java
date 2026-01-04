package com.hrm.payrollservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.payrollservice.dto.request.UpdateSalaryStructureRequest;
import com.hrm.payrollservice.service.SalaryStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll/salary-structure")
@RequiredArgsConstructor
public class SalaryStructureController {

    private final SalaryStructureService salaryService;

    @PostMapping("/update")
    public BaseResponse<Void> updateSalary(
            @RequestBody UpdateSalaryStructureRequest request
    ) {
        salaryService.updateSalary(request);
        return BaseResponse.success(null);
    }
}
