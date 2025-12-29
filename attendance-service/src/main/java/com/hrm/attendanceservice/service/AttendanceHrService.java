package com.hrm.attendanceservice.service;

import com.hrm.attendanceservice.dto.response.HrAttendanceByDateResponse;
import com.hrm.attendanceservice.dto.response.HrAttendanceByMonthResponse;
import com.hrm.attendanceservice.entity.AttendanceDailySummaryEntity;
import com.hrm.attendanceservice.repository.AttendanceDailySummaryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceHrService {

    AttendanceDailySummaryRepository summaryRepository;

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

}

