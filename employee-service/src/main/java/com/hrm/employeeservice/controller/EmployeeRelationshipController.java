package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.EmployeeRelationshipCreateRequest;
import com.hrm.employeeservice.dto.request.EmployeeRelationshipUpdateRequest;
import com.hrm.employeeservice.dto.response.EmployeeRelationshipResponse;
import com.hrm.employeeservice.service.EmployeeRelationshipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "employee-relationships")
@RestController
@RequestMapping("/employee-relationships")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeRelationshipController {

    EmployeeRelationshipService relationshipService;

    /** CREATE LIST */
    @PostMapping("/create")
    BaseResponse<List<EmployeeRelationshipResponse>> create(
            @RequestBody @Valid EmployeeRelationshipCreateRequest request) {
        return BaseResponse.success(relationshipService.create(request));
    }

    /** UPDATE LIST (BY EMPLOYEE) */
    @PostMapping("/update/{employeeId}")
    BaseResponse<List<EmployeeRelationshipResponse>> update(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody @Valid EmployeeRelationshipUpdateRequest request) {
        return BaseResponse.success(
                relationshipService.update(employeeId, request)
        );
    }

    /** GET LIST BY EMPLOYEE */
    @GetMapping("/list-by-employee/{employeeId}")
    BaseResponse<List<EmployeeRelationshipResponse>> listByEmployee(
            @PathVariable("employeeId") Long employeeId) {
        return BaseResponse.success(
                relationshipService.getByEmployee(employeeId)
        );
    }

    /** DELETE ALL */
    @DeleteMapping("/delete-by-employee/{employeeId}")
    BaseResponse<Void> deleteByEmployee(@PathVariable("employeeId") Long employeeId) {
        relationshipService.deleteByEmployee(employeeId);
        return BaseResponse.success(null);
    }
}
