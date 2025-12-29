package com.hrm.attendanceservice.service;

import com.hrm.attendanceservice.dto.request.AttendanceCheckInRequest;
import com.hrm.attendanceservice.dto.request.AttendanceCheckOutRequest;
import com.hrm.attendanceservice.dto.response.MyMonthAttendanceItemResponse;
import com.hrm.attendanceservice.dto.response.MyTodayAttendanceResponse;
import com.hrm.attendanceservice.entity.*;
import com.hrm.attendanceservice.repository.*;
import com.hrm.attendanceservice.util.GeoUtils;
import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceService {

    AttendanceLogRepository logRepository;
    AttendanceDailySummaryRepository summaryRepository;
    AttendanceShiftRepository shiftRepository;
    AttendanceLocationRuleRepository locationRuleRepository;
    WorkCalendarRepository calendarRepository;

    /* ========================= CHECK IN ========================= */
    @Transactional
    public void checkIn(String userId, AttendanceCheckInRequest request) {

        Long employeeId = Long.valueOf(userId);
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        /* ===== 1. Calendar ===== */
        DayType dayType = calendarRepository.findByWorkDate(today)
                .map(WorkCalendarEntity::getDayType)
                .orElse(DayType.WORKING);

        /* ===== 2. Location validation ===== */
        LocationCheckResult locationResult = validateLocation(
                request.getLatitude().doubleValue(),
                request.getLongitude().doubleValue()
        );

        /* ===== 3. Ghi log (LUÔN GHI) ===== */
        AttendanceLogEntity log = AttendanceLogEntity.builder()
                .employeeId(employeeId)
                .checkTime(now)
                .checkType(AttendanceCheckType.IN)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .locationRule(locationResult.rule())
                .isValidLocation(locationResult.valid())
                .deviceInfo(request.getDeviceInfo())
                .createdAt(now)
                .build();

        logRepository.save(log);

        /* ===== 4. Daily summary ===== */
        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(employeeId, today)
                        .orElseGet(() -> AttendanceDailySummaryEntity.builder()
                                .employeeId(employeeId)
                                .workDate(today)
                                .shift(shiftRepository.findAll().getFirst())
                                .lateMinutes(0)
                                .earlyMinutes(0)
                                .workMinutes(0)
                                .status(dayType == DayType.WORKING
                                        ? AttendanceStatus.NORMAL
                                        : AttendanceStatus.OT)
                                .createdAt(now)
                                .build()
                        );

        // chỉ set check-in lần đầu hợp lệ
        if (summary.getCheckInTime() == null && locationResult.valid()) {
            summary.setCheckInTime(now);
        }

        summaryRepository.save(summary);
    }

    /* ========================= CHECK OUT ========================= */
    @Transactional
    public void checkOut(String userId, AttendanceCheckOutRequest request) {

        Long employeeId = Long.valueOf(userId);
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        AttendanceDailySummaryEntity summary =
                summaryRepository.findByEmployeeIdAndWorkDate(employeeId, today)
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Chưa có dữ liệu công ngày hôm nay"
                        ));

        /* ===== 1. Location ===== */
        LocationCheckResult locationResult = validateLocation(
                request.getLatitude().doubleValue(),
                request.getLongitude().doubleValue()
        );

        /* ===== 2. Ghi log ===== */
        AttendanceLogEntity log = AttendanceLogEntity.builder()
                .employeeId(employeeId)
                .checkTime(now)
                .checkType(AttendanceCheckType.OUT)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .locationRule(locationResult.rule())
                .isValidLocation(locationResult.valid())
                .deviceInfo(request.getDeviceInfo())
                .createdAt(now)
                .build();

        logRepository.save(log);

        /* ===== 3. Update summary ===== */
        if (locationResult.valid() && summary.getCheckInTime() != null) {

            if (summary.getCheckOutTime() == null ||
                    now.isAfter(summary.getCheckOutTime())) {

                summary.setCheckOutTime(now);

                long minutes =
                        Duration.between(summary.getCheckInTime(), now).toMinutes();

                summary.setWorkMinutes((int) minutes);
            }
        }

        summaryRepository.save(summary);
    }

    /* ========================= LOCATION CHECK ========================= */
    private LocationCheckResult validateLocation(double lat, double lng) {

        List<AttendanceLocationRuleEntity> rules =
                locationRuleRepository.findAllByIsActiveTrue();

        for (AttendanceLocationRuleEntity rule : rules) {

            double distance = GeoUtils.distanceInMeters(
                    lat,
                    lng,
                    rule.getLatitude().doubleValue(),
                    rule.getLongitude().doubleValue()
            );

            if (distance <= rule.getRadiusMeter()) {
                return new LocationCheckResult(true, rule);
            }
        }

        return new LocationCheckResult(false, null);
    }

    /* ========================= HELPER RECORD ========================= */
    private record LocationCheckResult(
            boolean valid,
            AttendanceLocationRuleEntity rule
    ) {}


    public MyTodayAttendanceResponse getMyToday(String userId) {

        Long employeeId = Long.valueOf(userId);
        LocalDate today = LocalDate.now();

        return summaryRepository
                .findByEmployeeIdAndWorkDate(employeeId, today)
                .map(s -> MyTodayAttendanceResponse.builder()
                        .workDate(today)
                        .checkInTime(s.getCheckInTime())
                        .checkOutTime(s.getCheckOutTime())
                        .status(s.getStatus())
                        .workMinutes(s.getWorkMinutes())
                        .build()
                )
                .orElse(
                        MyTodayAttendanceResponse.builder()
                                .workDate(today)
                                .status(AttendanceStatus.ABSENT)
                                .workMinutes(0)
                                .build()
                );
    }


    public List<MyMonthAttendanceItemResponse> getMyMonth(
            String userId,
            String month) {

        Long employeeId = Long.valueOf(userId);

        LocalDate from = LocalDate.parse(month + "-01");
        LocalDate to = from.withDayOfMonth(from.lengthOfMonth());

        return summaryRepository
                .findAllByEmployeeIdAndWorkDateBetween(employeeId, from, to)
                .stream()
                .map(s -> MyMonthAttendanceItemResponse.builder()
                        .workDate(s.getWorkDate())
                        .status(s.getStatus())
                        .workMinutes(s.getWorkMinutes())
                        .lateMinutes(s.getLateMinutes())
                        .earlyMinutes(s.getEarlyMinutes())
                        .build()
                )
                .toList();
    }


}
