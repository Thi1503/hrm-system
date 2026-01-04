package com.hrm.payrollservice.dto.response;

import com.hrm.payrollservice.enums.WorkType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMonthTimesheetItemResponse {
    private LocalDate workDate;
    private WorkType workType;

    private Integer workMinutes;
    private Integer lateMinutes;
    private Integer earlyMinutes;
    private Integer otMinutes;

    private String note;
}
