package com.hrm.notificationservice.repository;

import com.hrm.notificationservice.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByEmployeeIdOrderByCreatedAtDesc(Long employeeId);

    @Query("""
        select count(n)
        from NotificationEntity n
        where n.employeeId = :employeeId
          and n.isRead = false
    """)
    long countUnread(Long employeeId);

    List<NotificationEntity> findByIdInAndEmployeeId(List<Long> ids, Long employeeId);
}
