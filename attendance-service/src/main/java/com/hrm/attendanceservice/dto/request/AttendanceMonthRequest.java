package com.hrm.attendanceservice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceMonthRequest {

    private Long employeeId;

    /**
     * Định dạng: yyyy-MM (VD: 2025-01)
     */
    private String yearMonth;
}

