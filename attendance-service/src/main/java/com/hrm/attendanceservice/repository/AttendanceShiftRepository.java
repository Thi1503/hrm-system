package com.hrm.attendanceservice.repository;

import com.hrm.attendanceservice.entity.AttendanceShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceShiftRepository
        extends JpaRepository<AttendanceShiftEntity, Long> {
}
