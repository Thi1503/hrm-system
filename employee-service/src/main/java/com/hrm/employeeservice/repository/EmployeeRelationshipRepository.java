package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.EmployeeRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRelationshipRepository
        extends JpaRepository<EmployeeRelationship, Long> {

    List<EmployeeRelationship> findByEmployee_Id(Long employeeId);

    void deleteByEmployee_Id(Long employeeId);
}
