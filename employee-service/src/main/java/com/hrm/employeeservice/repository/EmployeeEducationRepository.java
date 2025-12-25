package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.EmployeeEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeEducationRepository
        extends JpaRepository<EmployeeEducation, Long> {

    List<EmployeeEducation> findByEmployee_Id(Long employeeId);

    void deleteByEmployee_Id(Long employeeId);
}
