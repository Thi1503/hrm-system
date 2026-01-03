package com.hrm.attendanceservice.service;

import com.hrm.attendanceservice.entity.AttendanceDailySummaryEntity;
import com.hrm.attendanceservice.entity.AttendanceShiftEntity;
import com.hrm.attendanceservice.entity.AttendanceStatus;
import com.hrm.attendanceservice.kafka.event.AttendanceApprovalEvent;
import com.hrm.attendanceservice.repository.AttendanceDailySummaryRepository;
import com.hrm.attendanceservice.repository.AttendanceShiftRepository;
import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.hrm.attendanceservice.kafka.event.RequestType.EXPLANATION;

@Service
@RequiredArgsConstructor
public class AttendanceApprovalService {

    private final AttendanceDailySummaryRepository summaryRepository;
    private final AttendanceShiftRepository shiftRepository;

    public void handle(AttendanceApprovalEvent event) {

        switch (event.getRequestType()) {
            case EXPLANATION -> handleExplanation(event);
            case LEAVE -> handleLeave(event);
            case OT -> handleOt(event);
            case REMOTE -> handleRemote(event);
        }
    }

    private void handleExplanation(AttendanceApprovalEvent e) {

        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(
                        e.getEmployeeId(), e.getWorkDate()
                ).orElseThrow();

        switch (e.getExplanationType()) {
            case "LATE", "EARLY" -> {
                summary.setLateMinutes(0);
                summary.setEarlyMinutes(0);
                summary.setStatus(AttendanceStatus.NORMAL);
            }
            case "ABSENT" -> summary.setStatus(AttendanceStatus.NORMAL);
        }

        summaryRepository.save(summary);
    }

    private void handleLeave(AttendanceApprovalEvent e) {

        AttendanceShiftEntity defaultShift =
                shiftRepository.findAll().stream()
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Chưa cấu hình ca làm việc"
                        ));


        LocalDate date = e.getFromDate();
        while (!date.isAfter(e.getToDate())) {

            AttendanceDailySummaryEntity summary =
                    summaryRepository.findByEmployeeIdAndWorkDate(
                            e.getEmployeeId(), date
                    ).orElse(
                            AttendanceDailySummaryEntity.builder()
                                    .employeeId(e.getEmployeeId())
                                    .workDate(date)
                                    .shift(defaultShift)
                                    .lateMinutes(0)
                                    .earlyMinutes(0)
                                    .workMinutes(0)
                                    .build()
                    );

            summary.setStatus(AttendanceStatus.OFF);
            summaryRepository.save(summary);

            date = date.plusDays(1);
        }
    }

    private void handleOt(AttendanceApprovalEvent e) {

        AttendanceShiftEntity defaultShift =
                shiftRepository.findAll().stream()
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Chưa cấu hình ca làm việc"
                        ));


        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(
                        e.getEmployeeId(), e.getWorkDate()
                ).orElse(
                        AttendanceDailySummaryEntity.builder()
                                .employeeId(e.getEmployeeId())
                                .workDate(e.getWorkDate())
                                .shift(defaultShift)
                                .lateMinutes(0)
                                .earlyMinutes(0)
                                .workMinutes(0)
                                .build()
                );

        summary.setStatus(AttendanceStatus.OT);
        summaryRepository.save(summary);
    }

    private void handleRemote(AttendanceApprovalEvent e) {

        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(
                        e.getEmployeeId(), e.getWorkDate()
                ).orElseThrow();

        summary.setStatus(AttendanceStatus.NORMAL);
        summaryRepository.save(summary);
    }
}
