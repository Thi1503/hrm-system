package com.hrm.attendanceservice.repository;

import com.hrm.attendanceservice.entity.AttendanceDailySummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceDailySummaryRepository
        extends JpaRepository<AttendanceDailySummaryEntity, Long> {

    Optional<AttendanceDailySummaryEntity>
    findByEmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);

    List<AttendanceDailySummaryEntity>
    findAllByEmployeeIdAndWorkDateBetween(
            Long employeeId,
            LocalDate from,
            LocalDate to
    );

    /* ================== ADD THÊM ================== */

    List<AttendanceDailySummaryEntity>
    findAllByWorkDate(LocalDate workDate);

    List<AttendanceDailySummaryEntity>
    findAllByEmployeeIdAndWorkDate(
            Long employeeId,
            LocalDate workDate
    );
}

