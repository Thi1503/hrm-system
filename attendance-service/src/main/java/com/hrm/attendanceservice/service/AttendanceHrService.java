package com.hrm.attendanceservice.service;

import com.hrm.attendanceservice.dto.request.AttendanceApproveLocationRequest;
import com.hrm.attendanceservice.dto.request.AttendanceManualAdjustRequest;
import com.hrm.attendanceservice.dto.response.HrAttendanceByDateResponse;
import com.hrm.attendanceservice.dto.response.HrAttendanceByMonthResponse;
import com.hrm.attendanceservice.entity.AttendanceDailySummaryEntity;
import com.hrm.attendanceservice.entity.AttendanceLogEntity;
import com.hrm.attendanceservice.entity.AttendanceStatus;
import com.hrm.attendanceservice.repository.AttendanceDailySummaryRepository;
import com.hrm.attendanceservice.repository.AttendanceLogRepository;
import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceHrService {

    AttendanceDailySummaryRepository summaryRepository;
    AttendanceLogRepository logRepository;

    public List<HrAttendanceByDateResponse> getByDate(
            LocalDate date,
            Long employeeId
    ) {

        List<AttendanceDailySummaryEntity> list =
                employeeId == null
                        ? summaryRepository.findAllByWorkDate(date)
                        : summaryRepository.findAllByEmployeeIdAndWorkDate(employeeId, date);

        return list.stream()
                .map(s -> HrAttendanceByDateResponse.builder()
                        .employeeId(s.getEmployeeId())
                        .workDate(s.getWorkDate())
                        .checkInTime(s.getCheckInTime())
                        .checkOutTime(s.getCheckOutTime())
                        .lateMinutes(s.getLateMinutes())
                        .earlyMinutes(s.getEarlyMinutes())
                        .workMinutes(s.getWorkMinutes())
                        .status(s.getStatus())
                        .build()
                )
                .toList();
    }

    public List<HrAttendanceByMonthResponse> getByMonth(
            String month,
            Long employeeId
    ) {
        LocalDate from = LocalDate.parse(month + "-01");
        LocalDate to = from.withDayOfMonth(from.lengthOfMonth());

        List<AttendanceDailySummaryEntity> list =
                employeeId == null
                        ? summaryRepository.findAllByWorkDateBetween(from, to)
                        : summaryRepository.findAllByEmployeeIdAndWorkDateBetween(
                        employeeId, from, to);

        return list.stream()
                .map(s -> HrAttendanceByMonthResponse.builder()
                        .employeeId(s.getEmployeeId())
                        .workDate(s.getWorkDate())
                        .status(s.getStatus())
                        .workMinutes(s.getWorkMinutes())
                        .lateMinutes(s.getLateMinutes())
                        .earlyMinutes(s.getEarlyMinutes())
                        .build()
                )
                .toList();
    }

    @Transactional
    public void manualAdjust(AttendanceManualAdjustRequest request) {

        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(
                        request.getEmployeeId(),
                        request.getWorkDate()
                ).orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy công để điều chỉnh"
                ));

        summary.setCheckInTime(request.getCheckInTime());
        summary.setCheckOutTime(request.getCheckOutTime());

        if (request.getCheckInTime() != null &&
                request.getCheckOutTime() != null) {

            long minutes = Duration.between(
                    request.getCheckInTime(),
                    request.getCheckOutTime()
            ).toMinutes();

            summary.setWorkMinutes((int) minutes);
            summary.setStatus(AttendanceStatus.NORMAL);
        }

        summaryRepository.save(summary);
    }


    @Transactional
    public void approveInvalidLocation(AttendanceApproveLocationRequest request) {

        AttendanceLogEntity log = logRepository.findById(request.getAttendanceLogId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy log chấm công"
                ));

        log.setIsValidLocation(request.getApprove());
        logRepository.save(log);
    }



}

