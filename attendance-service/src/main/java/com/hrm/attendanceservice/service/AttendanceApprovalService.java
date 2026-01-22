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

    /* ================= EXPLANATION ================= */

    private void handleExplanation(AttendanceApprovalEvent e) {


        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(
                        e.getEmployeeId(), e.getWorkDate()
                ).orElseGet(() -> {
                    //  Case: quên check-in / check-out / quên chấm công
                    AttendanceDailySummaryEntity s = new AttendanceDailySummaryEntity();
                    s.setEmployeeId(e.getEmployeeId());
                    s.setWorkDate(e.getWorkDate());
                    s.setStatus(AttendanceStatus.ABSENT); // mặc định
                    s.setLateMinutes(0);
                    s.setEarlyMinutes(0);
                    s.setWorkMinutes(0);
                    return s;
                });

        switch (e.getExplanationType()) {

            case "LATE" -> {
                summary.setLateMinutes(0);

                if (summary.getEarlyMinutes() == 0) {
                    summary.setStatus(AttendanceStatus.NORMAL);
                }
            }

            case "EARLY" -> {
                summary.setEarlyMinutes(0);

                if (summary.getLateMinutes() == 0) {
                    summary.setStatus(AttendanceStatus.NORMAL);
                }
            }

            case "ABSENT" -> {
                // Giải trình quên chấm công
                summary.setStatus(AttendanceStatus.NORMAL);
            }

            default -> throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Loại giải trình không hợp lệ"
            );
        }

        summaryRepository.save(summary);
    }


    /* ================= LEAVE ================= */

    private void handleLeave(AttendanceApprovalEvent e) {

        AttendanceShiftEntity defaultShift = getDefaultShift();

        LocalDate date = e.getFromDate();
        while (!date.isAfter(e.getToDate())) {

            LocalDate finalDate = date;
            AttendanceDailySummaryEntity summary =
                    summaryRepository.findByEmployeeIdAndWorkDate(
                            e.getEmployeeId(), date
                    ).orElseGet(() ->
                            AttendanceDailySummaryEntity.builder()
                                    .employeeId(e.getEmployeeId())
                                    .workDate(finalDate)
                                    .shift(defaultShift)
                                    .lateMinutes(0)
                                    .earlyMinutes(0)
                                    .workMinutes(0)
                                    .status(AttendanceStatus.OFF)
                                    .build()
                    );

            summary.setStatus(AttendanceStatus.OFF);
            summaryRepository.save(summary);

            date = date.plusDays(1);
        }
    }

    /* ================= OT ================= */

    private void handleOt(AttendanceApprovalEvent e) {

        AttendanceShiftEntity defaultShift = getDefaultShift();

        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(
                        e.getEmployeeId(), e.getWorkDate()
                ).orElseGet(() ->
                        AttendanceDailySummaryEntity.builder()
                                .employeeId(e.getEmployeeId())
                                .workDate(e.getWorkDate())
                                .shift(defaultShift)
                                .lateMinutes(0)
                                .earlyMinutes(0)
                                .workMinutes(0)
                                .status(AttendanceStatus.OT)
                                .build()
                );

        summary.setStatus(AttendanceStatus.OT);
        summaryRepository.save(summary);
    }

    /* ================= REMOTE ================= */

    private void handleRemote(AttendanceApprovalEvent e) {

        AttendanceShiftEntity defaultShift = getDefaultShift();

        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(
                        e.getEmployeeId(), e.getWorkDate()
                ).orElseGet(() ->
                        AttendanceDailySummaryEntity.builder()
                                .employeeId(e.getEmployeeId())
                                .workDate(e.getWorkDate())
                                .shift(defaultShift)
                                .lateMinutes(0)
                                .earlyMinutes(0)
                                .workMinutes(0)
                                .status(AttendanceStatus.NORMAL)
                                .build()
                );

        summary.setStatus(AttendanceStatus.NORMAL);
        summaryRepository.save(summary);
    }

    /* ================= COMMON ================= */

    private AttendanceShiftEntity getDefaultShift() {
        return shiftRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() ->
                        new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Chưa cấu hình ca làm việc"
                        )
                );
    }
}

