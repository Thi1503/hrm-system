package com.hrm.requestapprovalservice.service;


import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.requestapprovalservice.client.EmployeeClient;
import com.hrm.requestapprovalservice.dto.request.ApproveApprovalRequest;
import com.hrm.requestapprovalservice.dto.response.client.EmployeeSimpleResponse;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerExplanationApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerLeaveApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerOtApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerRemoteApprovalResponse;
import com.hrm.requestapprovalservice.entity.*;
import com.hrm.requestapprovalservice.enums.ApprovalAction;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.ApproverRole;
import com.hrm.requestapprovalservice.mapper.manager.ManagerExplanationApprovalMapper;
import com.hrm.requestapprovalservice.mapper.manager.ManagerLeaveApprovalMapper;
import com.hrm.requestapprovalservice.mapper.manager.ManagerOtApprovalMapper;
import com.hrm.requestapprovalservice.mapper.manager.ManagerRemoteApprovalMapper;
import com.hrm.requestapprovalservice.repository.*;
import jakarta.transaction.Transactional;
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
    ApprovalHistoryRepository approvalHistoryRepository;



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


    @Transactional
    public void approve(Long managerId,
                        ApproveApprovalRequest request) {

        switch (request.getRequestType()) {
            case EXPLANATION -> approveExplanation(managerId, request);
            case LEAVE -> approveLeave(managerId, request);
            case OT -> approveOt(managerId, request);
            case REMOTE -> approveRemote(managerId, request);
            default -> throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Loại đơn không hợp lệ"
            );
        }
    }

    /* ================= EXPLANATION ================= */

    private void approveExplanation(Long managerId,
                                    ApproveApprovalRequest req) {

        AttendanceExplanationEntity entity =
                explanationRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        validatePendingManager(entity.getStatus());

        entity.setStatus(ApprovalStatus.PENDING_HR);
        explanationRepository.save(entity);

        saveHistory(req, managerId);
    }

    /* ================= LEAVE ================= */

    private void approveLeave(Long managerId,
                              ApproveApprovalRequest req) {

        LeaveRequestEntity entity =
                leaveRequestRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        validatePendingManager(entity.getStatus());

        entity.setStatus(ApprovalStatus.PENDING_HR);
        leaveRequestRepository.save(entity);

        saveHistory(req, managerId);
    }

    /* ================= OT ================= */

    private void approveOt(Long managerId,
                           ApproveApprovalRequest req) {

        OtRequestEntity entity =
                otRequestRepository.findById(req.getRequestId())
                        .orElseThrow();

        validatePendingManager(entity.getStatus());

        entity.setStatus(ApprovalStatus.PENDING_HR);
        otRequestRepository.save(entity);

        saveHistory(req, managerId);
    }

    /* ================= REMOTE ================= */

    private void approveRemote(Long managerId,
                               ApproveApprovalRequest req) {

        RemoteRequestEntity entity =
                remoteRequestRepository.findById(req.getRequestId())
                        .orElseThrow();

        validatePendingManager(entity.getStatus());

        entity.setStatus(ApprovalStatus.PENDING_HR);
        remoteRequestRepository.save(entity);

        saveHistory(req, managerId);
    }

    /* ================= COMMON ================= */

    private void validatePendingManager(ApprovalStatus status) {
        if (status != ApprovalStatus.PENDING_MANAGER) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Chỉ được duyệt đơn ở trạng thái PENDING_MANAGER"
            );
        }
    }

    private void saveHistory(ApproveApprovalRequest req,
                             Long managerId) {

        approvalHistoryRepository.save(
                ApprovalHistoryEntity.builder()
                        .requestType(req.getRequestType())
                        .requestId(req.getRequestId())
                        .approverId(managerId)
                        .approverRole(ApproverRole.MANAGER)
                        .action(ApprovalAction.APPROVE)
                        .comment(req.getComment())
                        .build()
        );
    }

    private BusinessException notFound() {
        return new BusinessException(
                ErrorCode.NOT_FOUND,
                "Không tìm thấy đơn"
        );
    }


}

