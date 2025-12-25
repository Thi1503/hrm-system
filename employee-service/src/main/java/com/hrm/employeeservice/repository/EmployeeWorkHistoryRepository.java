package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.EmployeeWorkHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeWorkHistoryRepository
        extends JpaRepository<EmployeeWorkHistory, Long> {
}
