package com.hrm.employeeservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.employeeservice.dto.request.EmployeeRelationshipCreateRequest;
import com.hrm.employeeservice.dto.request.EmployeeRelationshipItemRequest;
import com.hrm.employeeservice.dto.request.EmployeeRelationshipUpdateRequest;
import com.hrm.employeeservice.dto.response.EmployeeRelationshipResponse;
import com.hrm.employeeservice.entity.Employee;
import com.hrm.employeeservice.entity.EmployeeRelationship;
import com.hrm.employeeservice.mapper.EmployeeRelationshipMapper;
import com.hrm.employeeservice.repository.EmployeeRelationshipRepository;
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
public class EmployeeRelationshipService {

    EmployeeRelationshipRepository relationshipRepository;
    EmployeeRepository employeeRepository;
    EmployeeRelationshipMapper relationshipMapper;

    /** CREATE (LIST) */
    @Transactional
    public List<EmployeeRelationshipResponse> create(EmployeeRelationshipCreateRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy nhân viên"
                ));

        List<EmployeeRelationship> entities = request.getRelationships()
                .stream()
                .map(item -> mapToEntity(item, employee))
                .toList();

        return relationshipRepository.saveAll(entities)
                .stream()
                .map(relationshipMapper::toResponse)
                .toList();
    }

    /** UPDATE (REPLACE LIST) */
    @Transactional
    public List<EmployeeRelationshipResponse> update(Long employeeId,
                                                     EmployeeRelationshipUpdateRequest request) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy nhân viên"
                ));

        // Xoá toàn bộ người thân cũ
        relationshipRepository.deleteByEmployee_Id(employeeId);

        List<EmployeeRelationship> entities = request.getRelationships()
                .stream()
                .map(item -> mapToEntity(item, employee))
                .toList();

        return relationshipRepository.saveAll(entities)
                .stream()
                .map(relationshipMapper::toResponse)
                .toList();
    }

    /** GET LIST BY EMPLOYEE */
    public List<EmployeeRelationshipResponse> getByEmployee(Long employeeId) {
        return relationshipRepository.findByEmployee_Id(employeeId)
                .stream()
                .map(relationshipMapper::toResponse)
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
        relationshipRepository.deleteByEmployee_Id(employeeId);
    }

    private EmployeeRelationship mapToEntity(EmployeeRelationshipItemRequest item,
                                             Employee employee) {
        EmployeeRelationship entity = new EmployeeRelationship();
        entity.setEmployee(employee);
        entity.setFullName(item.getFullName());
        entity.setRelationshipType(item.getRelationshipType());
        entity.setPhoneNumber(item.getPhoneNumber());
        entity.setCitizenId(item.getCitizenId());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }
}
