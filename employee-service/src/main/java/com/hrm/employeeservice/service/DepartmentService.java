package com.hrm.employeeservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.employeeservice.dto.request.DepartmentRequest;
import com.hrm.employeeservice.dto.response.DepartmentResponse;
import com.hrm.employeeservice.entity.Department;
import com.hrm.employeeservice.mapper.DepartmentMapper;
import com.hrm.employeeservice.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentMapper departmentMapper;


    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName()))
            throw new BusinessException(ErrorCode.DATA_ALREADY_EXISTS, "Tên phòng ban đã tồn tại");

        Department department = departmentMapper.toEntity(request);
        department.setCreatedAt(LocalDateTime.now());
        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    /** UPDATE */
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy phòng ban"
                ));

        if (!department.getName().equals(request.getName())
                && departmentRepository.existsByName(request.getName())) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Tên phòng ban đã tồn tại"
            );
        }

        department.setName(request.getName());
        department.setHeadEmployeeId(request.getHeadEmployeeId());
        department.setDescription(request.getDescription());

        return departmentMapper.toResponse(
                departmentRepository.save(department)
        );
    }

    /** GET DETAIL */
    public DepartmentResponse getDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy phòng ban"
                ));

        return departmentMapper.toResponse(department);
    }

    /** GET LIST */
    public List<DepartmentResponse> getDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    /** DELETE */
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Không tìm thấy phòng ban"
            );
        }
        departmentRepository.deleteById(id);
    }
}
