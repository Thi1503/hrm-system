package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.EmployeeContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeContractRepository extends JpaRepository<EmployeeContract, Long> {

    boolean existsByContractNumber(String contractNumber);

    List<EmployeeContract> findByEmployee_Id(Long employeeId);
}
