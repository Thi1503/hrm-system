package com.hrm.notificationservice.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class MarkReadBatchRequest {
    private List<Long> notificationIds;
}
