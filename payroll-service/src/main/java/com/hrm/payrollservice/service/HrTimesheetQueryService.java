package com.hrm.payrollservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.payrollservice.dto.response.MyMonthTimesheetItemResponse;
import com.hrm.payrollservice.dto.response.MyMonthTimesheetResponse;
import com.hrm.payrollservice.entity.TimesheetMonthEntity;
import com.hrm.payrollservice.repository.TimesheetDailyRepository;
import com.hrm.payrollservice.repository.TimesheetMonthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HrTimesheetQueryService {

    private final TimesheetMonthRepository monthRepo;
    private final TimesheetDailyRepository dailyRepo;

    public MyMonthTimesheetResponse getEmployeeMonth(Long employeeId, String month) {

        TimesheetMonthEntity monthEntity = monthRepo
                .findByEmployeeIdAndMonth(employeeId, month)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Timesheet not found for employeeId=" + employeeId + ", month=" + month
                ));

        var dailies = dailyRepo
                .findAllByTimesheetMonthIdOrderByWorkDateAsc(monthEntity.getId());

        List<MyMonthTimesheetItemResponse> items = dailies.stream()
                .map(d -> MyMonthTimesheetItemResponse.builder()
                        .workDate(d.getWorkDate())
                        .workType(d.getWorkType())
                        .workMinutes(d.getWorkMinutes())
                        .lateMinutes(d.getLateMinutes())
                        .earlyMinutes(d.getEarlyMinutes())
                        .otMinutes(d.getOtMinutes())
                        .note(d.getNote())
                        .build())
                .toList();

        return MyMonthTimesheetResponse.builder()
                .timesheetMonthId(monthEntity.getId())
                .employeeId(monthEntity.getEmployeeId())
                .month(monthEntity.getMonth())
                .status(monthEntity.getStatus())
                .totalWorkDays(monthEntity.getTotalWorkDays())
                .totalWorkMinutes(monthEntity.getTotalWorkMinutes())
                .lateMinutes(monthEntity.getLateMinutes())
                .earlyMinutes(monthEntity.getEarlyMinutes())
                .otHours(monthEntity.getOtHours())
                .leaveDays(monthEntity.getLeaveDays())
                .items(items)
                .build();
    }
}
