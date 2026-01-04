package com.hrm.payrollservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateTimesheetResponse {

    /**
     * Tháng generate (YYYY-MM)
     */
    private String month;

    /**
     * Tổng số nhân viên trong hệ thống
     */
    private int totalEmployees;

    /**
     * Số bảng công được generate (tránh generate trùng)
     */
    private int generated;
}
