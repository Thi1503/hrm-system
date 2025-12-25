package com.hrm.employeeservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.employeeservice.dto.request.EmployeeEducationCreateRequest;
import com.hrm.employeeservice.dto.request.EmployeeEducationItemRequest;
import com.hrm.employeeservice.dto.request.EmployeeEducationUpdateRequest;
import com.hrm.employeeservice.dto.response.EmployeeEducationResponse;
import com.hrm.employeeservice.entity.Employee;
import com.hrm.employeeservice.entity.EmployeeEducation;
import com.hrm.employeeservice.mapper.EmployeeEducationMapper;
import com.hrm.employeeservice.repository.EmployeeEducationRepository;
import com.hrm.employeeservice.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeEducationService {

    EmployeeEducationRepository educationRepository;
    EmployeeRepository employeeRepository;
    EmployeeEducationMapper educationMapper;

    /** CREATE LIST */
    @Transactional
    public List<EmployeeEducationResponse> create(EmployeeEducationCreateRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy nhân viên"
                ));

        List<EmployeeEducation> entities = request.getEducations()
                .stream()
                .map(item -> mapToEntity(item, employee))
                .toList();

        return educationRepository.saveAll(entities)
                .stream()
                .map(educationMapper::toResponse)
                .toList();
    }

    /** UPDATE LIST (REPLACE) */
    @Transactional
    public List<EmployeeEducationResponse> update(Long employeeId,
                                                  EmployeeEducationUpdateRequest request) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy nhân viên"
                ));

        // Xoá toàn bộ học vấn cũ
        educationRepository.deleteByEmployee_Id(employeeId);

        List<EmployeeEducation> entities = request.getEducations()
                .stream()
                .map(item -> mapToEntity(item, employee))
                .toList();

        return educationRepository.saveAll(entities)
                .stream()
                .map(educationMapper::toResponse)
                .toList();
    }

    /** GET LIST BY EMPLOYEE */
    public List<EmployeeEducationResponse> getByEmployee(Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Không tìm thấy nhân viên"
            );
        }

        return educationRepository.findByEmployee_Id(employeeId)
                .stream()
                .map(educationMapper::toResponse)
                .toList();
    }

    /** DELETE ALL BY EMPLOYEE */
    @Transactional
    public void deleteByEmployee(Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Không tìm thấy nhân viên"
            );
        }

        educationRepository.deleteByEmployee_Id(employeeId);
    }

    /* ===============================
       PRIVATE MAPPER
       =============================== */
    private EmployeeEducation mapToEntity(EmployeeEducationItemRequest item,
                                          Employee employee) {

        EmployeeEducation entity = new EmployeeEducation();
        entity.setEmployee(employee);
        entity.setDegree(item.getDegree());
        entity.setMajor(item.getMajor());
        entity.setSchool(item.getSchool());
        entity.setStartDate(item.getStartDate());
        entity.setEndDate(item.getEndDate());
        entity.setDescription(item.getDescription());
        entity.setCreatedAt(LocalDateTime.now());

        return entity;
    }
}

