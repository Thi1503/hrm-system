package com.hrm.payrollservice.repository;

import com.hrm.payrollservice.entity.PayrollDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollDetailRepository extends JpaRepository<PayrollDetailEntity, Long> {
    List<PayrollDetailEntity> findAllByPayrollId(Long payrollId);
    void deleteAllByPayrollId(Long payrollId);
}
