package com.hrm.requestapprovalservice.kafka.producer;

import com.hrm.requestapprovalservice.kafka.event.AttendanceApprovalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceApprovalProducer {

    private final KafkaTemplate<String, AttendanceApprovalEvent> kafkaTemplate;

    public void publish(AttendanceApprovalEvent event) {
        kafkaTemplate.send(
                "attendance-approval-event",
                event.getEmployeeId().toString(),
                event
        );
    }
}

