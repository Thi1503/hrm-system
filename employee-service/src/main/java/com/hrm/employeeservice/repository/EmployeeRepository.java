package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByCode(String code);
}
