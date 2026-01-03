package com.hrm.attendanceservice.kafka.consumer;

import com.hrm.attendanceservice.kafka.event.AttendanceApprovalEvent;
import com.hrm.attendanceservice.service.AttendanceApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceApprovalConsumer {

    private final AttendanceApprovalService approvalService;

    @KafkaListener(
            topics = "attendance-approval-event",
            groupId = "attendance-service"
    )
    public void consume(AttendanceApprovalEvent event) {
        approvalService.handle(event);
    }


}

