package com.hrm.requestapprovalservice.repository;

import com.hrm.requestapprovalservice.entity.ApprovalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistoryEntity, Long> {
}
