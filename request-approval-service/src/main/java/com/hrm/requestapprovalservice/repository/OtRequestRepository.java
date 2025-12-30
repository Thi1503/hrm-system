package com.hrm.requestapprovalservice.repository;

import com.hrm.requestapprovalservice.entity.OtRequestEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OtRequestRepository
        extends JpaRepository<OtRequestEntity, Long> {

    List<OtRequestEntity> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeIdAndOtDateAndStatusNot(
            Long employeeId,
            LocalDate otDate,
            ApprovalStatus status
    );
}
