package com.hrm.payrollservice.repository;

import com.hrm.payrollservice.entity.SalaryStructureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SalaryStructureRepository
        extends JpaRepository<SalaryStructureEntity, Long> {

    Optional<SalaryStructureEntity>
    findTopByEmployeeIdAndEffectiveFromLessThanEqualOrderByEffectiveFromDesc(
            Long employeeId, LocalDate date
    );
}
