package com.hrm.requestapprovalservice.service;

import com.hrm.requestapprovalservice.client.EmployeeClient;
import com.hrm.requestapprovalservice.dto.response.client.EmployeeInternalItemResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrExplanationApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrLeaveApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrOtApprovalResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrRemoteApprovalResponse;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.mapper.hr.HrExplanationApprovalMapper;
import com.hrm.requestapprovalservice.mapper.hr.HrLeaveApprovalMapper;
import com.hrm.requestapprovalservice.mapper.hr.HrOtApprovalMapper;
import com.hrm.requestapprovalservice.mapper.hr.HrRemoteApprovalMapper;
import com.hrm.requestapprovalservice.repository.AttendanceExplanationRepository;
import com.hrm.requestapprovalservice.repository.LeaveRequestRepository;
import com.hrm.requestapprovalservice.repository.OtRequestRepository;
import com.hrm.requestapprovalservice.repository.RemoteRequestRepository;
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

    HrExplanationApprovalMapper explanationMapper;
    HrLeaveApprovalMapper leaveApprovalMapper;
    HrOtApprovalMapper otApprovalMapper;
    HrRemoteApprovalMapper remoteApprovalMapper;

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


}

