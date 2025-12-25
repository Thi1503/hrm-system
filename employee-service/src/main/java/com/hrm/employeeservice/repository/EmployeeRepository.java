package com.hrm.employeeservice.repository;

import com.hrm.employeeservice.entity.Employee;
import com.hrm.employeeservice.entity.EmploymentStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByCode(String code);

    List<Employee> findByManagerId(Long managerId);

    Optional<Employee> findByAccountId(String accountId);

    @Query("""
                SELECT e FROM Employee e
                WHERE (:keyword IS NULL
                    OR LOWER(e.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(e.code) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
                AND (:departmentId IS NULL OR e.department.id = :departmentId)
                AND (:positionId IS NULL OR e.position.id = :positionId)
                AND (:status IS NULL OR e.employmentStatus = :status)
                AND (:managerId IS NULL OR e.manager.id = :managerId)
            """)
    Page<Employee> search(
            @Param("keyword") String keyword,
            @Param("departmentId") Long departmentId,
            @Param("positionId") Long positionId,
            @Param("status") EmploymentStatus status,
            @Param("managerId") Long managerId,
            Pageable pageable
    );

}

