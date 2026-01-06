package com.hrm.notificationservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.notificationservice.dto.request.MarkReadBatchRequest;
import com.hrm.notificationservice.dto.response.NotificationResponse;
import com.hrm.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /* ===================== QUERY ===================== */

    @GetMapping("/my")
    public BaseResponse<List<NotificationResponse>> getMyNotifications(
            @RequestHeader("X-User-Id") Long employeeId
    ) {
        return BaseResponse.success(
                notificationService.getMyNotifications(employeeId)
        );
    }

    @GetMapping("/my/unread-count")
    public BaseResponse<Map<String, Long>> countUnread(
            @RequestHeader("X-User-Id") Long employeeId
    ) {
        return BaseResponse.success(
                Map.of("unreadCount", notificationService.countUnread(employeeId))
        );
    }

    /* ===================== COMMAND ===================== */

    @PostMapping("/read/{id}")
    public BaseResponse<Void> markRead(
            @RequestHeader("X-User-Id") Long employeeId,
            @PathVariable Long id
    ) {
        notificationService.markRead(employeeId, id);
        return BaseResponse.success(null);
    }

    @PostMapping("/read/batch")
    public BaseResponse<Void> markReadBatch(
            @RequestHeader("X-User-Id") Long employeeId,
            @RequestBody MarkReadBatchRequest request
    ) {
        notificationService.markReadBatch(employeeId, request);
        return BaseResponse.success(null);
    }
}
