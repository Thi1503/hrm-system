package com.hrm.requestapprovalservice.service;


import com.hrm.requestapprovalservice.client.EmployeeClient;
import com.hrm.requestapprovalservice.dto.request.EmployeeSimpleResponse;
import com.hrm.requestapprovalservice.dto.response.ManagerExplanationApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.ManagerLeaveApprovalResponse;
import com.hrm.requestapprovalservice.entity.AttendanceExplanationEntity;
import com.hrm.requestapprovalservice.entity.LeaveRequestEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.mapper.ManagerExplanationApprovalMapper;
import com.hrm.requestapprovalservice.mapper.ManagerLeaveApprovalMapper;
import com.hrm.requestapprovalservice.repository.AttendanceExplanationRepository;
import com.hrm.requestapprovalservice.repository.LeaveRequestRepository;
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
    ManagerExplanationApprovalMapper managerExplanationApprovalMapper;
    LeaveRequestRepository leaveRequestRepository;
    ManagerLeaveApprovalMapper leaveApprovalMapper;

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
                .map(e -> {
                    ManagerExplanationApprovalResponse res =
                            managerExplanationApprovalMapper.toResponse(e);
                    res.setEmployeeName(
                            employeeNameMap.get(e.getEmployeeId())
                    );
                    return res;
                })
                .toList();

    }

    public List<ManagerLeaveApprovalResponse>
    getPendingLeavesForManager(Long managerId) {

        Map<Long, String> employeeMap = getEmployeeMap(managerId);
        if (employeeMap.isEmpty()) return List.of();

        List<LeaveRequestEntity> entities =
                leaveRequestRepository.findByEmployeeIdInAndStatus(
                        employeeMap.keySet().stream().toList(),
                        ApprovalStatus.PENDING_MANAGER
                );

        return entities.stream()
                .map(e -> {
                    ManagerLeaveApprovalResponse res =
                            leaveApprovalMapper.toResponse(e);
                    res.setEmployeeName(employeeMap.get(e.getEmployeeId()));
                    return res;
                })
                .toList();
    }


    private Map<Long, String> getEmployeeMap(Long managerId) {

        // Call employee-service
        var response = employeeClient.getByManager(managerId);

        if (response == null || response.getData() == null) {
            return Map.of();
        }

        return response.getData()
                .stream()
                .collect(Collectors.toMap(
                        EmployeeSimpleResponse::getId,
                        EmployeeSimpleResponse::getFullName
                ));
    }

}

