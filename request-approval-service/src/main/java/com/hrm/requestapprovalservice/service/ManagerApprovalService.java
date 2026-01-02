package com.hrm.requestapprovalservice.service;


import com.hrm.requestapprovalservice.client.EmployeeClient;
import com.hrm.requestapprovalservice.dto.request.EmployeeSimpleResponse;
import com.hrm.requestapprovalservice.dto.response.ManagerExplanationApprovalResponse;
import com.hrm.requestapprovalservice.entity.AttendanceExplanationEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.repository.AttendanceExplanationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagerApprovalService {

    EmployeeClient employeeClient;
    AttendanceExplanationRepository explanationRepository;

    public List<ManagerExplanationApprovalResponse>
    getPendingExplanationsForManager(Long managerId) {

        // 1️⃣ Call employee-service → lấy danh sách nhân viên
        List<EmployeeSimpleResponse> employees =
                employeeClient.getByManager(managerId).getData();

        if (employees == null || employees.isEmpty()) {
            return Collections.emptyList();
        }

        // 2️⃣ Map employeeId → employeeName
        Map<Long, String> employeeNameMap =
                employees.stream()
                        .collect(Collectors.toMap(
                                EmployeeSimpleResponse::getId,
                                EmployeeSimpleResponse::getFullName
                        ));

        List<Long> employeeIds = new ArrayList<>(employeeNameMap.keySet());

        // 3️⃣ Query DB request-approval-service
        List<AttendanceExplanationEntity> entities =
                explanationRepository.findByEmployeeIdInAndStatus(
                        employeeIds,
                        ApprovalStatus.PENDING_MANAGER
                );

        // 4️⃣ Map response + ghép tên
        return entities.stream()
                .map(e -> ManagerExplanationApprovalResponse.builder()
                        .requestId(e.getId())
                        .employeeId(e.getEmployeeId())
                        .employeeName(employeeNameMap.get(e.getEmployeeId()))
                        .workDate(e.getWorkDate())
                        .explanationType(e.getExplanationType())
                        .reason(e.getReason())
                        .status(e.getStatus())
                        .build()
                )
                .toList();
    }
}

