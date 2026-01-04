package com.hrm.requestapprovalservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.requestapprovalservice.dto.response.internal.ApprovedRequestForPayrollResponse;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.RequestType;
import com.hrm.requestapprovalservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalPayrollService {

    private final AttendanceExplanationRepository explanationRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final OtRequestRepository otRequestRepository;
    private final RemoteRequestRepository remoteRequestRepository;



    public List<ApprovedRequestForPayrollResponse>
    getApprovedRequestsByMonth(String month) {

        YearMonth yearMonth;
        try {
            yearMonth = YearMonth.parse(month); // yyyy-MM
        } catch (DateTimeParseException e) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Tháng không hợp lệ, định dạng đúng: yyyy-MM"
            );
        }

        LocalDate fromDate = yearMonth.atDay(1);
        LocalDate toDate = yearMonth.atEndOfMonth();

        return getApprovedRequests(fromDate, toDate);
    }





    public List<ApprovedRequestForPayrollResponse> getApprovedRequests(
            LocalDate fromDate,
            LocalDate toDate
    ) {

        List<ApprovedRequestForPayrollResponse> result = new ArrayList<>();

        /* ================= EXPLANATION ================= */
        explanationRepository.findByStatus(ApprovalStatus.APPROVED)
                .forEach(e -> {
                    if (inRange(e.getWorkDate(), fromDate, toDate)) {
                        result.add(
                                ApprovedRequestForPayrollResponse.builder()
                                        .requestType(RequestType.EXPLANATION)
                                        .requestId(e.getId())
                                        .employeeId(e.getEmployeeId())
                                        .workDate(e.getWorkDate())
                                        .fromDate(null)
                                        .toDate(null)
                                        .startTime(null)
                                        .endTime(null)
                                        .explanationType(
                                                e.getExplanationType() != null
                                                        ? e.getExplanationType().name()
                                                        : null
                                        )
                                        .build()
                        );
                    }
                });

        /* ================= LEAVE ================= */
        leaveRequestRepository.findByStatus(ApprovalStatus.APPROVED)
                .forEach(e -> {
                    if (overlap(e.getFromDate(), e.getToDate(), fromDate, toDate)) {
                        result.add(
                                ApprovedRequestForPayrollResponse.builder()
                                        .requestType(RequestType.LEAVE)
                                        .requestId(e.getId())
                                        .employeeId(e.getEmployeeId())
                                        .workDate(null)
                                        .fromDate(e.getFromDate())
                                        .toDate(e.getToDate())
                                        .startTime(null)
                                        .endTime(null)
                                        .explanationType(null)
                                        .build()
                        );
                    }
                });

        /* ================= OT ================= */
        otRequestRepository.findByStatus(ApprovalStatus.APPROVED)
                .forEach(e -> {
                    if (inRange(e.getOtDate(), fromDate, toDate)) {
                        result.add(
                                ApprovedRequestForPayrollResponse.builder()
                                        .requestType(RequestType.OT)
                                        .requestId(e.getId())
                                        .employeeId(e.getEmployeeId())
                                        .workDate(e.getOtDate())
                                        .fromDate(null)
                                        .toDate(null)
                                        .startTime(e.getStartTime())
                                        .endTime(e.getEndTime())
                                        .explanationType(null)
                                        .build()
                        );
                    }
                });

        /* ================= REMOTE ================= */
        remoteRequestRepository.findByStatus(ApprovalStatus.APPROVED)
                .forEach(e -> {
                    if (inRange(e.getRemoteDate(), fromDate, toDate)) {
                        result.add(
                                ApprovedRequestForPayrollResponse.builder()
                                        .requestType(RequestType.REMOTE)
                                        .requestId(e.getId())
                                        .employeeId(e.getEmployeeId())
                                        .workDate(e.getRemoteDate())
                                        .fromDate(null)
                                        .toDate(null)
                                        .startTime(null)
                                        .endTime(null)
                                        .explanationType(null)
                                        .build()
                        );
                    }
                });

        return result;
    }

    /* ================= HELPER ================= */

    private boolean inRange(LocalDate date,
                            LocalDate from,
                            LocalDate to) {

        if (date == null) return false;
        if (from != null && date.isBefore(from)) return false;
        return to == null || !date.isAfter(to);
    }

    private boolean overlap(LocalDate start,
                            LocalDate end,
                            LocalDate from,
                            LocalDate to) {

        if (from == null && to == null) return true;
        if (from != null && end.isBefore(from)) return false;
        if (to != null && start.isAfter(to)) return false;
        return true;
    }
}
