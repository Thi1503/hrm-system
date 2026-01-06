package com.hrm.notificationservice.dto.response;

import com.hrm.notificationservice.entity.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String title;
    private String content;
    private NotificationType notificationType;

    private String referenceType;
    private Long referenceId;

    private Boolean isRead;
    private LocalDateTime createdAt;
}
