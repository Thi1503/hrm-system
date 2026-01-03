package com.hrm.requestapprovalservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.requestapprovalservice.client.EmployeeClient;
import com.hrm.requestapprovalservice.dto.request.ApproveApprovalRequest;
import com.hrm.requestapprovalservice.dto.response.client.EmployeeInternalItemResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrExplanationApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrLeaveApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrOtApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrRemoteApprovalResponse;
import com.hrm.requestapprovalservice.entity.*;
import com.hrm.requestapprovalservice.enums.ApprovalAction;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.ApproverRole;
import com.hrm.requestapprovalservice.enums.RequestType;
import com.hrm.requestapprovalservice.kafka.event.AttendanceApprovalEvent;
import com.hrm.requestapprovalservice.kafka.producer.AttendanceApprovalProducer;
import com.hrm.requestapprovalservice.mapper.hr.HrExplanationApprovalMapper;
import com.hrm.requestapprovalservice.mapper.hr.HrLeaveApprovalMapper;
import com.hrm.requestapprovalservice.mapper.hr.HrOtApprovalMapper;
import com.hrm.requestapprovalservice.mapper.hr.HrRemoteApprovalMapper;
import com.hrm.requestapprovalservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HrApprovalService {

    EmployeeClient employeeClient;
    AttendanceExplanationRepository explanationRepository;
    LeaveRequestRepository leaveRequestRepository;
    OtRequestRepository otRequestRepository;
    RemoteRequestRepository remoteRequestRepository;
    ApprovalHistoryRepository approvalHistoryRepository;

    HrExplanationApprovalMapper explanationMapper;
    HrLeaveApprovalMapper leaveApprovalMapper;
    HrOtApprovalMapper otApprovalMapper;
    HrRemoteApprovalMapper remoteApprovalMapper;

    private final AttendanceApprovalProducer producer;

    private Map<Long, EmployeeInternalItemResponse> getEmployeeMap() {

        var response = employeeClient.getAllActiveEmployee();
        if (response == null || response.getData() == null) {
            return Map.of();
        }

        return response.getData()
                .stream()
                .collect(Collectors.toMap(
                        EmployeeInternalItemResponse::getId,
                        e -> e
                ));
    }

    public List<HrExplanationApprovalResponse> getPendingExplanations() {

        Map<Long, EmployeeInternalItemResponse> employeeMap = getEmployeeMap();
        if (employeeMap.isEmpty()) return List.of();

        return explanationRepository
                .findByEmployeeIdInAndStatus(
                        employeeMap.keySet().stream().toList(),
                        ApprovalStatus.PENDING_HR
                )
                .stream()
                .map(entity -> {
                    HrExplanationApprovalResponse res =
                            explanationMapper.toResponse(entity);

                    EmployeeInternalItemResponse emp =
                            employeeMap.get(entity.getEmployeeId());

                    if (emp != null) {
                        res.setEmployeeName(emp.getFullName());
                        res.setManagerId(emp.getManagerId());
                        res.setManagerName(emp.getManagerName());
                        res.setDepartmentId(emp.getDepartmentId());
                        res.setDepartmentName(emp.getDepartmentName());
                        res.setPositionId(emp.getPositionId());
                        res.setPositionName(emp.getPositionName());
                    }
                    return res;
                })
                .toList();
    }

    public List<HrLeaveApprovalResponse> getPendingLeavesForHr() {

        Map<Long, EmployeeInternalItemResponse> employeeMap = getEmployeeMap();
        if (employeeMap.isEmpty()) return List.of();

        return leaveRequestRepository
                .findByEmployeeIdInAndStatus(
                        employeeMap.keySet().stream().toList(),
                        ApprovalStatus.PENDING_HR
                )
                .stream()
                .map(entity -> {
                    HrLeaveApprovalResponse res =
                            leaveApprovalMapper.toResponse(entity);

                    EmployeeInternalItemResponse emp =
                            employeeMap.get(entity.getEmployeeId());

                    if (emp != null) {
                        mapEmployee(res, emp);
                    }
                    return res;
                })
                .toList();
    }


    public List<HrOtApprovalResponse> getPendingOtsForHr() {

        Map<Long, EmployeeInternalItemResponse> employeeMap = getEmployeeMap();
        if (employeeMap.isEmpty()) return List.of();

        return otRequestRepository
                .findByEmployeeIdInAndStatus(
                        employeeMap.keySet().stream().toList(),
                        ApprovalStatus.PENDING_HR
                )
                .stream()
                .map(entity -> {
                    HrOtApprovalResponse res =
                            otApprovalMapper.toResponse(entity);

                    EmployeeInternalItemResponse emp =
                            employeeMap.get(entity.getEmployeeId());

                    if (emp != null) {
                        mapEmployee(res, emp);
                    }
                    return res;
                })
                .toList();
    }

    public List<HrRemoteApprovalResponse> getPendingRemotesForHr() {

        Map<Long, EmployeeInternalItemResponse> employeeMap = getEmployeeMap();
        if (employeeMap.isEmpty()) return List.of();

        return remoteRequestRepository
                .findByEmployeeIdInAndStatus(
                        employeeMap.keySet().stream().toList(),
                        ApprovalStatus.PENDING_HR
                )
                .stream()
                .map(entity -> {
                    HrRemoteApprovalResponse res =
                            remoteApprovalMapper.toResponse(entity);

                    EmployeeInternalItemResponse emp =
                            employeeMap.get(entity.getEmployeeId());

                    if (emp != null) {
                        mapEmployee(res, emp);
                    }
                    return res;
                })
                .toList();
    }




    private void mapEmployee(Object response,
                             EmployeeInternalItemResponse emp) {

        if (response instanceof HrLeaveApprovalResponse r) {
            r.setEmployeeId(emp.getId());
            r.setEmployeeName(emp.getFullName());
            r.setManagerId(emp.getManagerId());
            r.setManagerName(emp.getManagerName());
            r.setDepartmentId(emp.getDepartmentId());
            r.setDepartmentName(emp.getDepartmentName());
            r.setPositionId(emp.getPositionId());
            r.setPositionName(emp.getPositionName());
        }

        if (response instanceof HrOtApprovalResponse r) {
            r.setEmployeeId(emp.getId());
            r.setEmployeeName(emp.getFullName());
            r.setManagerId(emp.getManagerId());
            r.setManagerName(emp.getManagerName());
            r.setDepartmentId(emp.getDepartmentId());
            r.setDepartmentName(emp.getDepartmentName());
            r.setPositionId(emp.getPositionId());
            r.setPositionName(emp.getPositionName());
        }

        if (response instanceof HrRemoteApprovalResponse r) {
            r.setEmployeeId(emp.getId());
            r.setEmployeeName(emp.getFullName());
            r.setManagerId(emp.getManagerId());
            r.setManagerName(emp.getManagerName());
            r.setDepartmentId(emp.getDepartmentId());
            r.setDepartmentName(emp.getDepartmentName());
            r.setPositionId(emp.getPositionId());
            r.setPositionName(emp.getPositionName());
        }
    }



    @Transactional
    public void approve(Long hrId, ApproveApprovalRequest request) {

        switch (request.getRequestType()) {
            case EXPLANATION -> approveExplanation(hrId, request);
            case LEAVE -> approveLeave(hrId, request);
            case OT -> approveOt(hrId, request);
            case REMOTE -> approveRemote(hrId, request);
            default -> throw invalid();
        }
    }

    private void approveExplanation(Long hrId, ApproveApprovalRequest req) {

        AttendanceExplanationEntity entity =
                explanationRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        validatePendingHr(entity.getStatus());

        entity.setStatus(ApprovalStatus.APPROVED);
        explanationRepository.save(entity);

        saveHistory(req, hrId);
        producer.publish(buildExplanationEvent(entity));
    }

    private void approveLeave(Long hrId, ApproveApprovalRequest req) {

        LeaveRequestEntity entity =
                leaveRequestRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        validatePendingHr(entity.getStatus());

        entity.setStatus(ApprovalStatus.APPROVED);
        leaveRequestRepository.save(entity);

        saveHistory(req, hrId);
        producer.publish(buildLeaveEvent(entity));
    }

    private void approveOt(Long hrId, ApproveApprovalRequest req) {

        OtRequestEntity entity =
                otRequestRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        validatePendingHr(entity.getStatus());

        entity.setStatus(ApprovalStatus.APPROVED);
        otRequestRepository.save(entity);

        saveHistory(req, hrId);
        producer.publish(buildOtEvent(entity));
    }

    private void approveRemote(Long hrId, ApproveApprovalRequest req) {

        RemoteRequestEntity entity =
                remoteRequestRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        validatePendingHr(entity.getStatus());

        entity.setStatus(ApprovalStatus.APPROVED);
        remoteRequestRepository.save(entity);

        saveHistory(req, hrId);
        producer.publish(buildRemoteEvent(entity));
    }

    private void validatePendingHr(ApprovalStatus status) {
        if (status != ApprovalStatus.PENDING_HR) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Chỉ được duyệt đơn ở trạng thái PENDING_HR"
            );
        }
    }

    private void saveHistory(ApproveApprovalRequest req, Long hrId) {
        approvalHistoryRepository.save(
                ApprovalHistoryEntity.builder()
                        .requestType(req.getRequestType())
                        .requestId(req.getRequestId())
                        .approverId(hrId)
                        .approverRole(ApproverRole.HR)
                        .action(ApprovalAction.APPROVE)
                        .comment(req.getComment())
                        .build()
        );
    }

    /* ===== BUILD EVENT ===== */

    private AttendanceApprovalEvent buildExplanationEvent(AttendanceExplanationEntity e) {
        return AttendanceApprovalEvent.builder()
                .requestType(RequestType.EXPLANATION)
                .employeeId(e.getEmployeeId())
                .workDate(e.getWorkDate())
                .explanationType(e.getExplanationType().name())
                .build();
    }

    private AttendanceApprovalEvent buildLeaveEvent(LeaveRequestEntity e) {
        return AttendanceApprovalEvent.builder()
                .requestType(RequestType.LEAVE)
                .employeeId(e.getEmployeeId())
                .fromDate(e.getFromDate())
                .toDate(e.getToDate())
                .build();
    }

    private AttendanceApprovalEvent buildOtEvent(OtRequestEntity e) {
        return AttendanceApprovalEvent.builder()
                .requestType(RequestType.OT)
                .employeeId(e.getEmployeeId())
                .workDate(e.getOtDate())
                .build();
    }

    private AttendanceApprovalEvent buildRemoteEvent(RemoteRequestEntity e) {
        return AttendanceApprovalEvent.builder()
                .requestType(RequestType.REMOTE)
                .employeeId(e.getEmployeeId())
                .workDate(e.getRemoteDate())
                .build();
    }

    private BusinessException notFound() {
        return new BusinessException(ErrorCode.NOT_FOUND, "Không tìm thấy đơn");
    }

    private BusinessException invalid() {
        return new BusinessException(ErrorCode.INVALID_REQUEST, "Loại đơn không hợp lệ");
    }

}

