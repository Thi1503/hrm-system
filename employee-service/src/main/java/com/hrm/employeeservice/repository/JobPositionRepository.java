package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition,Long> {
    boolean existsByName(String name);
    Optional<JobPosition> findByName(String name);
}
