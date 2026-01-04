package com.hrm.payrollservice.repository;

import com.hrm.payrollservice.entity.TimesheetDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimesheetDailyRepository
        extends JpaRepository<TimesheetDailyEntity, Long> {
    void deleteAllByTimesheetMonthIdIn(List<Long> timesheetMonthIds);

    List<TimesheetDailyEntity> findAllByTimesheetMonthIdOrderByWorkDateAsc(Long timesheetMonthId);

}

