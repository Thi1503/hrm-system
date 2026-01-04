package com.hrm.employeeservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.employeeservice.dto.response.internalResponse.EmployeeInfoResponse;
import com.hrm.employeeservice.dto.response.internalResponse.EmployeeInternalItemResponse;
import com.hrm.employeeservice.dto.response.internalResponse.EmployeeSimpleResponse;
import com.hrm.employeeservice.entity.Employee;
import com.hrm.employeeservice.entity.EmploymentStatus;
import com.hrm.employeeservice.mapper.EmployeeMapper;
import com.hrm.employeeservice.repository.DepartmentRepository;
import com.hrm.employeeservice.repository.EmployeeRepository;
import com.hrm.employeeservice.repository.EmployeeWorkHistoryRepository;
import com.hrm.employeeservice.repository.JobPositionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeInternalService {

    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    JobPositionRepository jobPositionRepository;
    EmployeeMapper employeeMapper;
    EmployeeWorkHistoryRepository employeeWorkHistoryRepository;

    public EmployeeInfoResponse getByAccountId(String accountId) {
        Employee employee = employeeRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy nhân viên"));

        return EmployeeInfoResponse.builder()
                .employeeId(employee.getId())
                .fullName(employee.getFullName())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getName())
                .positionName(employee.getPosition().getName())
                .build();
    }

    public List<EmployeeSimpleResponse> getEmployeesByManager(Long managerId) {
        return employeeRepository.findSimpleByManagerId(managerId);
    }

    public List<EmployeeInternalItemResponse> getAllActiveInternal() {
        return employeeRepository
                .findByEmploymentStatus(EmploymentStatus.ACTIVE)
                .stream()
                .map(employeeMapper::toInternalItem)
                .toList();
    }

    public List<EmployeeSimpleResponse> getAllEmployeesForPayroll() {
        return employeeRepository.findAllSimpleForPayroll();
    }



}
