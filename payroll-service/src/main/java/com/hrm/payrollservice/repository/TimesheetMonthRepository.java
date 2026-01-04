package com.hrm.payrollservice.repository;

import com.hrm.payrollservice.entity.TimesheetMonthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimesheetMonthRepository
        extends JpaRepository<TimesheetMonthEntity, Long> {

    boolean existsByEmployeeIdAndMonth(Long employeeId, String month);

    Optional<TimesheetMonthEntity>
    findByEmployeeIdAndMonth(Long employeeId, String month);

    List<TimesheetMonthEntity> findAllByMonth(String month);
}

