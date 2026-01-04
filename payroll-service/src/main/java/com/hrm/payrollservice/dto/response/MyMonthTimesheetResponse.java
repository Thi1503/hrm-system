package com.hrm.payrollservice.dto.response;

import com.hrm.payrollservice.enums.TimesheetStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMonthTimesheetResponse {

    private Long timesheetMonthId;
    private Long employeeId;
    private String month;
    private TimesheetStatus status;

    private BigDecimal totalWorkDays;
    private Integer totalWorkMinutes;
    private Integer lateMinutes;
    private Integer earlyMinutes;
    private BigDecimal otHours;
    private BigDecimal leaveDays;

    private List<MyMonthTimesheetItemResponse> items;
}
