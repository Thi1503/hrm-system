package com.hrm.payrollservice.repository;

import com.hrm.payrollservice.entity.TimesheetDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetDailyRepository
        extends JpaRepository<TimesheetDailyEntity, Long> {
}

