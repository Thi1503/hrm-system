package com.hrm.attendanceservice.repository;

import com.hrm.attendanceservice.entity.AttendanceLocationRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceLocationRuleRepository
        extends JpaRepository<AttendanceLocationRuleEntity, Long> {

    List<AttendanceLocationRuleEntity> findAllByIsActiveTrue();
}
