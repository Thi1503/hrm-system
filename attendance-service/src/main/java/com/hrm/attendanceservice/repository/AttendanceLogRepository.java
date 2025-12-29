package com.hrm.attendanceservice.repository;

import com.hrm.attendanceservice.entity.AttendanceCheckType;
import com.hrm.attendanceservice.entity.AttendanceLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceLogRepository
        extends JpaRepository<AttendanceLogEntity, Long> {

    Optional<AttendanceLogEntity> findTopByEmployeeIdAndCheckTypeOrderByCheckTimeDesc(
            Long employeeId,
            AttendanceCheckType checkType
    );

    List<AttendanceLogEntity> findByEmployeeIdAndCheckTimeBetween(
            Long employeeId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<AttendanceLogEntity>
    findAllByEmployeeIdAndCheckTimeBetweenOrderByCheckTimeAsc(
            Long employeeId,
            LocalDateTime from,
            LocalDateTime to
    );
}

