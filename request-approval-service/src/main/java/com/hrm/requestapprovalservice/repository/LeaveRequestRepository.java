package com.hrm.requestapprovalservice.repository;

import com.hrm.requestapprovalservice.entity.LeaveRequestEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository
        extends JpaRepository<LeaveRequestEntity, Long> {

    List<LeaveRequestEntity> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeIdAndStatusNotAndFromDateLessThanEqualAndToDateGreaterThanEqual(
            Long employeeId,
            ApprovalStatus status,
            LocalDate toDate,
            LocalDate fromDate
    );
}
