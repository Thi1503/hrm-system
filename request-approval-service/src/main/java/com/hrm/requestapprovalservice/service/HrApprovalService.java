package com.hrm.requestapprovalservice.service;

import com.hrm.requestapprovalservice.client.EmployeeClient;
import com.hrm.requestapprovalservice.dto.response.client.EmployeeInternalItemResponse;
import com.hrm.requestapprovalservice.dto.response.hr.HrExplanationApprovalResponse;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.mapper.hr.HrExplanationApprovalMapper;
import com.hrm.requestapprovalservice.repository.AttendanceExplanationRepository;
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
    HrExplanationApprovalMapper explanationMapper;

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
}

