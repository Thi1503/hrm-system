package com.hrm.attendanceservice.repository;

import com.hrm.attendanceservice.entity.AttendanceDailySummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceDailySummaryRepository
        extends JpaRepository<AttendanceDailySummaryEntity, Long> {

    Optional<AttendanceDailySummaryEntity>
    findByEmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);
}

