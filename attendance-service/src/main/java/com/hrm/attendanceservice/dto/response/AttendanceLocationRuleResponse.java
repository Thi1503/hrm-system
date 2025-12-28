package com.hrm.attendanceservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AttendanceLocationRuleResponse {

    Long id;
    String name;
    BigDecimal latitude;
    BigDecimal longitude;
    Integer radiusMeter;
    Boolean isActive;
    LocalDateTime createdAt;
}
