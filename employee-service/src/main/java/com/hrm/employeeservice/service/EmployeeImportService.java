package com.hrm.employeeservice.service;

import com.hrm.common.exception.BusinessException;
import com.hrm.common.enums.ErrorCode;
import com.hrm.employeeservice.dto.request.*;
import com.hrm.employeeservice.dto.response.*;
import com.hrm.employeeservice.entity.Employee;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeImportService {

    EmployeeService employeeService;
    EmployeeContractService contractService;
    EmployeeRelationshipService relationshipService;
    EmployeeEducationService educationService;

    @Transactional
    public EmployeeFullResponse importAll(EmployeeImportRequest request) {

        // 1️⃣ Create employee
        EmployeeResponse employee =
                employeeService.create(request.getEmployee());

        Long employeeId = employee.getId();

        // 2️⃣ Contract
        EmployeeContractResponse contract = null;
        if (request.getContract() != null) {
            request.getContract().setEmployeeId(employeeId);
            contract = contractService.create(request.getContract());
        }

        // 3️⃣ Relationships
        List<EmployeeRelationshipResponse> relationships = null;
        if (request.getRelationships() != null &&
                !request.getRelationships().isEmpty()) {

            relationships = relationshipService.create(
                    EmployeeRelationshipCreateRequest.builder()
                            .employeeId(employeeId)
                            .relationships(request.getRelationships())
                            .build()
            );
        }

        // 4️⃣ Educations
        List<EmployeeEducationResponse> educations = null;
        if (request.getEducations() != null &&
                !request.getEducations().isEmpty()) {

            educations = educationService.create(
                    EmployeeEducationCreateRequest.builder()
                            .employeeId(employeeId)
                            .educations(request.getEducations())
                            .build()
            );
        }

        return EmployeeFullResponse.builder()
                .employee(employee)
                .contract(contract)
                .relationships(relationships)
                .educations(educations)
                .build();
    }
}
