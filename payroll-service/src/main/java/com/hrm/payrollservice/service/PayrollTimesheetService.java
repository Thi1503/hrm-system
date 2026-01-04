package com.hrm.payrollservice.service;

import com.hrm.payrollservice.client.AttendanceClient;
import com.hrm.payrollservice.client.EmployeeClient;
import com.hrm.payrollservice.client.RequestApprovalClient;
import com.hrm.payrollservice.dto.response.GenerateTimesheetResponse;
import com.hrm.payrollservice.dto.response.client.ApprovedRequestForPayrollResponse;
import com.hrm.payrollservice.dto.response.client.EmployeeSimpleResponse;
import com.hrm.payrollservice.dto.response.client.PayrollAttendanceByMonthResponse;
import com.hrm.payrollservice.entity.TimesheetDailyEntity;
import com.hrm.payrollservice.entity.TimesheetMonthEntity;
import com.hrm.payrollservice.enums.RequestType;
import com.hrm.payrollservice.enums.WorkType;
import com.hrm.payrollservice.repository.TimesheetDailyRepository;
import com.hrm.payrollservice.repository.TimesheetMonthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollTimesheetService {

    private static final int STANDARD_WORK_MINUTES = 480; // 8h

    private final EmployeeClient employeeClient;
    private final AttendanceClient attendanceClient;
    private final RequestApprovalClient requestApprovalClient;

    private final TimesheetMonthRepository monthRepo;
    private final TimesheetDailyRepository dailyRepo;

    public GenerateTimesheetResponse generateMonth(String month) {

        List<EmployeeSimpleResponse> employees =
                employeeClient.getAllEmployees().getData();

        List<PayrollAttendanceByMonthResponse> attendances =
                attendanceClient.getForPayrollByMonth(month).getData();

        List<ApprovedRequestForPayrollResponse> requests =
                requestApprovalClient.getApprovedRequestsByMonth(month).getData();

        Map<Long, Map<LocalDate, PayrollAttendanceByMonthResponse>> attendanceMap =
                attendances.stream()
                        .collect(Collectors.groupingBy(
                                PayrollAttendanceByMonthResponse::getEmployeeId,
                                Collectors.toMap(
                                        PayrollAttendanceByMonthResponse::getWorkDate,
                                        a -> a
                                )
                        ));

        Map<Long, List<ApprovedRequestForPayrollResponse>> requestMap =
                requests.stream()
                        .collect(Collectors.groupingBy(
                                ApprovedRequestForPayrollResponse::getEmployeeId
                        ));

        YearMonth ym = YearMonth.parse(month);
        int daysInMonth = ym.lengthOfMonth();
        int generated = 0;

        for (EmployeeSimpleResponse emp : employees) {

            if (monthRepo.existsByEmployeeIdAndMonth(emp.getId(), month)) {
                continue; // tránh generate trùng
            }

            TimesheetMonthEntity monthEntity = TimesheetMonthEntity.builder()
                    .employeeId(emp.getId())
                    .month(month)
                    .totalWorkDays(BigDecimal.ZERO)
                    .totalWorkMinutes(0)
                    .lateMinutes(0)
                    .earlyMinutes(0)
                    .otHours(BigDecimal.ZERO)
                    .leaveDays(BigDecimal.ZERO)
                    .build();

            monthRepo.save(monthEntity);

            List<TimesheetDailyEntity> dailyList = new ArrayList<>();

            for (int d = 1; d <= daysInMonth; d++) {
                LocalDate date = ym.atDay(d);

                PayrollAttendanceByMonthResponse att =
                        attendanceMap
                                .getOrDefault(emp.getId(), Map.of())
                                .get(date);

                WorkType workType = WorkType.ABSENT;
                int workMinutes = 0;
                int late = 0;
                int early = 0;
                int otMinutes = 0;

                /* ===== Attendance ===== */
                if (att != null) {
                    int attWorkMinutes = safe(att.getWorkMinutes());
                    late = safe(att.getLateMinutes());
                    early = safe(att.getEarlyMinutes());

                    switch (att.getStatus()) {

                        case NORMAL, LATE, EARLY -> {
                            workType = WorkType.NORMAL;
                            workMinutes = Math.min(attWorkMinutes, STANDARD_WORK_MINUTES);
                        }

                        case OT -> {
                            workType = WorkType.OT;
                            otMinutes = attWorkMinutes;
                        }

                        case OFF -> {
                            // ngày nghỉ hợp lệ → không tính công, không phạt
                            workType = WorkType.ABSENT;
                            workMinutes = late = early = otMinutes = 0;
                        }

                        case ABSENT -> {
                            workType = WorkType.ABSENT;
                            workMinutes = late = early = otMinutes = 0;
                        }
                    }

                }

                /* ===== Request override ===== */
                for (ApprovedRequestForPayrollResponse r :
                        requestMap.getOrDefault(emp.getId(), List.of())) {

                    if (r.getRequestType() == RequestType.LEAVE &&
                            !date.isBefore(r.getFromDate()) &&
                            !date.isAfter(r.getToDate())) {

                        workType = WorkType.LEAVE;
                        workMinutes = late = early = otMinutes = 0;
                        break;
                    }

                    if (r.getRequestType() == RequestType.EXPLANATION &&
                            date.equals(r.getWorkDate())) {

                        late = 0;
                        early = 0;
                    }
                }

                /* ===== Save daily ===== */
                dailyList.add(
                        TimesheetDailyEntity.builder()
                                .timesheetMonthId(monthEntity.getId())
                                .workDate(date)
                                .workType(workType)
                                .workMinutes(workMinutes)
                                .lateMinutes(late)
                                .earlyMinutes(early)
                                .otMinutes(otMinutes)
                                .build()
                );

                /* ===== Accumulate month ===== */

                // Ngày công chuẩn
                if (workType == WorkType.NORMAL) {
                    monthEntity.setTotalWorkDays(
                            monthEntity.getTotalWorkDays().add(BigDecimal.ONE));

                    monthEntity.setTotalWorkMinutes(
                            monthEntity.getTotalWorkMinutes() + workMinutes);

                    monthEntity.setLateMinutes(
                            monthEntity.getLateMinutes() + late);

                    monthEntity.setEarlyMinutes(
                            monthEntity.getEarlyMinutes() + early);
                }

                // Nghỉ phép
                if (workType == WorkType.LEAVE) {
                    monthEntity.setLeaveDays(
                            monthEntity.getLeaveDays().add(BigDecimal.ONE));
                }

                // OT (không tính ngày công)
                if (workType == WorkType.OT && otMinutes > 0) {
                    monthEntity.setOtHours(
                            monthEntity.getOtHours().add(
                                    BigDecimal.valueOf(otMinutes)
                                            .divide(BigDecimal.valueOf(60))
                            )
                    );
                }
            }

            dailyRepo.saveAll(dailyList);
            monthRepo.save(monthEntity);
            generated++;
        }

        return new GenerateTimesheetResponse(
                month,
                employees.size(),
                generated
        );
    }

    private int safe(Integer v) {
        return v == null ? 0 : v;
    }
}
