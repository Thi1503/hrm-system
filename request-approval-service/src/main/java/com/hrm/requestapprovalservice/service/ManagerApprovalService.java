package com.hrm.requestapprovalservice.service;


import com.hrm.requestapprovalservice.client.EmployeeClient;
import com.hrm.requestapprovalservice.dto.response.client.EmployeeSimpleResponse;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerExplanationApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerLeaveApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerOtApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerRemoteApprovalResponse;
import com.hrm.requestapprovalservice.entity.AttendanceExplanationEntity;
import com.hrm.requestapprovalservice.entity.LeaveRequestEntity;
import com.hrm.requestapprovalservice.entity.OtRequestEntity;
import com.hrm.requestapprovalservice.entity.RemoteRequestEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.mapper.manager.ManagerExplanationApprovalMapper;
import com.hrm.requestapprovalservice.mapper.manager.ManagerLeaveApprovalMapper;
import com.hrm.requestapprovalservice.mapper.manager.ManagerOtApprovalMapper;
import com.hrm.requestapprovalservice.mapper.manager.ManagerRemoteApprovalMapper;
import com.hrm.requestapprovalservice.repository.AttendanceExplanationRepository;
import com.hrm.requestapprovalservice.repository.LeaveRequestRepository;
import com.hrm.requestapprovalservice.repository.OtRequestRepository;
import com.hrm.requestapprovalservice.repository.RemoteRequestRepository;
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
    ManagerExplanationApprovalMapper explanationApprovalMapper;
    ManagerLeaveApprovalMapper leaveApprovalMapper;
    ManagerOtApprovalMapper otApprovalMapper;
    ManagerRemoteApprovalMapper remoteApprovalMapper;

    AttendanceExplanationRepository explanationRepository;
    LeaveRequestRepository leaveRequestRepository;
    OtRequestRepository otRequestRepository;
    RemoteRequestRepository remoteRequestRepository;



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
                            explanationApprovalMapper.toResponse(e);
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

    public List<ManagerOtApprovalResponse>
    getPendingOtsForManager(Long managerId) {

        Map<Long, String> employeeMap = getEmployeeMap(managerId);
        if (employeeMap.isEmpty()) return List.of();

        List<OtRequestEntity> entities =
                otRequestRepository.findByEmployeeIdInAndStatus(
                        employeeMap.keySet().stream().toList(),
                        ApprovalStatus.PENDING_MANAGER
                );

        return entities.stream()
                .map(e -> {
                    ManagerOtApprovalResponse res =
                            otApprovalMapper.toResponse(e);
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

    public List<ManagerRemoteApprovalResponse>
    getPendingRemotesForManager(Long managerId) {

        Map<Long, String> employeeMap = getEmployeeMap(managerId);
        if (employeeMap.isEmpty()) return List.of();

        List<RemoteRequestEntity> entities =
                remoteRequestRepository.findByEmployeeIdInAndStatus(
                        employeeMap.keySet().stream().toList(),
                        ApprovalStatus.PENDING_MANAGER
                );

        return entities.stream()
                .map(e -> {
                    ManagerRemoteApprovalResponse res =
                            remoteApprovalMapper.toResponse(e);
                    res.setEmployeeName(employeeMap.get(e.getEmployeeId()));
                    return res;
                })
                .toList();
    }


}

