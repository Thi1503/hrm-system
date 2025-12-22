package com.hrm.employeeservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.employeeservice.dto.request.EmployeeCreateRequest;
import com.hrm.employeeservice.dto.request.EmployeeUpdateRequest;
import com.hrm.employeeservice.dto.response.EmployeeResponse;
import com.hrm.employeeservice.entity.Department;
import com.hrm.employeeservice.entity.Employee;
import com.hrm.employeeservice.entity.JobPosition;
import com.hrm.employeeservice.mapper.EmployeeMapper;
import com.hrm.employeeservice.repository.DepartmentRepository;
import com.hrm.employeeservice.repository.EmployeeRepository;
import com.hrm.employeeservice.repository.JobPositionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {

    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    JobPositionRepository jobPositionRepository;
    EmployeeMapper employeeMapper;

    /** CREATE */
    public EmployeeResponse create(EmployeeCreateRequest request) {

        if (employeeRepository.existsByCode(request.getCode())) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Mã nhân viên đã tồn tại"
            );
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy phòng ban"));

        JobPosition position = jobPositionRepository.findById(request.getPositionId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy chức vụ"));

        Employee employee = new Employee();
        employee.setAccountId(request.getAccountId());
        employee.setFullName(request.getFullName());
        employee.setCode(request.getCode());
        employee.setGender(request.getGender());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setCitizenId(request.getCitizenId());
        employee.setIssuedDate(request.getIssuedDate());
        employee.setIssuedPlace(request.getIssuedPlace());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setEmail(request.getEmail());
        employee.setAddress(request.getAddress());
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setStartWorkDate(request.getStartWorkDate());
        employee.setEmploymentStatus(request.getEmploymentStatus());
        employee.setSalaryBasic(request.getSalaryBasic());
        employee.setExtraInfo(request.getExtraInfo());
        employee.setAvatarUrl(request.getAvatarUrl());
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());

        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy quản lý"));
            employee.setManager(manager);
        }

        return employeeMapper.toResponse(
                employeeRepository.save(employee)
        );
    }

    /** UPDATE */
    public EmployeeResponse update(Long id, EmployeeUpdateRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy nhân viên"));

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy phòng ban"));
            employee.setDepartment(department);
        }

        if (request.getPositionId() != null) {
            JobPosition position = jobPositionRepository.findById(request.getPositionId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy chức vụ"));
            employee.setPosition(position);
        }

        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy quản lý"));
            employee.setManager(manager);
        }

        employee.setFullName(request.getFullName());
        employee.setGender(request.getGender());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setCitizenId(request.getCitizenId());
        employee.setIssuedDate(request.getIssuedDate());
        employee.setIssuedPlace(request.getIssuedPlace());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setEmail(request.getEmail());
        employee.setAddress(request.getAddress());
        employee.setEndWorkDate(request.getEndWorkDate());
        employee.setEmploymentStatus(request.getEmploymentStatus());
        employee.setSalaryBasic(request.getSalaryBasic());
        employee.setExtraInfo(request.getExtraInfo());
        employee.setAvatarUrl(request.getAvatarUrl());
        employee.setUpdatedAt(LocalDateTime.now());

        return employeeMapper.toResponse(
                employeeRepository.save(employee)
        );
    }

    /** GET DETAIL */
    public EmployeeResponse getDetail(Long id) {
        return employeeMapper.toResponse(
                employeeRepository.findById(id)
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy nhân viên"))
        );
    }

    /** GET LIST */
    public List<EmployeeResponse> getList() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    /** DELETE */
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy nhân viên");
        }
        employeeRepository.deleteById(id);
    }
}
