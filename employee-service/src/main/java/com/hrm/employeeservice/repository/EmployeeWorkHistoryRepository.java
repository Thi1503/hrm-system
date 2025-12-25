package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.EmployeeWorkHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeWorkHistoryRepository
        extends JpaRepository<EmployeeWorkHistory, Long> {
    List<EmployeeWorkHistory> findByEmployee_IdOrderByFromDateDesc(Long employeeId);
}
