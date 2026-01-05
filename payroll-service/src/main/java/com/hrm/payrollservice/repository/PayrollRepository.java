package com.hrm.payrollservice.repository;

import com.hrm.payrollservice.entity.PayrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayrollRepository extends JpaRepository<PayrollEntity, Long> {
    Optional<PayrollEntity> findByEmployeeIdAndMonth(Long employeeId, String month);

}
