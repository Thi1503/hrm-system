package com.hrm.requestapprovalservice.repository;

import com.hrm.requestapprovalservice.entity.AttendanceExplanationEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceExplanationRepository
        extends JpaRepository<AttendanceExplanationEntity, Long> {

    List<AttendanceExplanationEntity> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeIdAndWorkDateAndStatusNot(
            Long employeeId,
            LocalDate workDate,
            ApprovalStatus status
    );

    List<AttendanceExplanationEntity>
    findByEmployeeIdInAndStatus(
            List<Long> employeeIds,
            ApprovalStatus status
    );
}
