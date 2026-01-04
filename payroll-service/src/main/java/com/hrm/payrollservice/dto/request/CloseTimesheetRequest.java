package com.hrm.payrollservice.dto.request;

import lombok.Data;

@Data
public class CloseTimesheetRequest {
    private Long employeeId;
    private String month; // YYYY-MM
}
