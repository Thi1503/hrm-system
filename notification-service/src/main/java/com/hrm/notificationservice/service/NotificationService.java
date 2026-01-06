package com.hrm.notificationservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.notificationservice.dto.request.MarkReadBatchRequest;
import com.hrm.notificationservice.dto.response.NotificationResponse;
import com.hrm.notificationservice.entity.NotificationEntity;
import com.hrm.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /* ===================== QUERY ===================== */

    public List<NotificationResponse> getMyNotifications(Long employeeId) {
        return notificationRepository
                .findByEmployeeIdOrderByCreatedAtDesc(employeeId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public long countUnread(Long employeeId) {
        return notificationRepository.countUnread(employeeId);
    }

    /* ===================== COMMAND ===================== */

    @Transactional
    public void markRead(Long employeeId, Long notificationId) {

        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Notification not found: " + notificationId
                ));

        if (!notification.getEmployeeId().equals(employeeId)) {
            throw new BusinessException(
                    ErrorCode.FORBIDDEN,
                    "You cannot read notification of another user"
            );
        }

        if (!notification.getIsRead()) {
            notification.setIsRead(true);
        }
    }


    @Transactional
    public void markReadBatch(Long employeeId, MarkReadBatchRequest request) {

        List<NotificationEntity> notifications =
                notificationRepository.findByIdInAndEmployeeId(
                        request.getNotificationIds(),
                        employeeId
                );

        if (notifications.isEmpty()) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "No notifications found for current user"
            );
        }

        notifications.forEach(n -> {
            if (!n.getIsRead()) {
                n.setIsRead(true);
            }
        });
    }


    /* ===================== MAPPER ===================== */

    private NotificationResponse toResponse(NotificationEntity n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .notificationType(n.getNotificationType())
                .referenceType(n.getReferenceType())
                .referenceId(n.getReferenceId())
                .isRead(n.getIsRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
