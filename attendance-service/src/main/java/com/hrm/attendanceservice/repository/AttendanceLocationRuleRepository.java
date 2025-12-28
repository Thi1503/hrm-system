package com.hrm.attendanceservice.repository;

import com.hrm.attendanceservice.entity.AttendanceLocationRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceLocationRuleRepository
        extends JpaRepository<AttendanceLocationRuleEntity, Long> {
}
