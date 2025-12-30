package com.hrm.requestapprovalservice.repository;

import com.hrm.requestapprovalservice.entity.RemoteRequestEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RemoteRequestRepository
        extends JpaRepository<RemoteRequestEntity, Long> {

    List<RemoteRequestEntity> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeIdAndRemoteDateAndStatusNot(
            Long employeeId,
            LocalDate remoteDate,
            ApprovalStatus status
    );
}


