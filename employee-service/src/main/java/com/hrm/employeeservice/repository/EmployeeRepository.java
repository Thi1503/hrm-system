package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByCode(String code);

    List<Employee> findByManagerId(Long managerId);
}
