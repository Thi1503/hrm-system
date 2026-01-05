package com.hrm.payrollservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.payrollservice.dto.request.CalculatePayrollRequest;
import com.hrm.payrollservice.dto.response.PayrollDetailResponse;
import com.hrm.payrollservice.dto.response.PayrollResponse;
import com.hrm.payrollservice.entity.*;
import com.hrm.payrollservice.enums.PayrollComponentType;
import com.hrm.payrollservice.enums.PayrollStatus;
import com.hrm.payrollservice.enums.TimesheetStatus;
import com.hrm.payrollservice.mapper.PayrollMapper;
import com.hrm.payrollservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollCalculateService {


    private final TimesheetMonthRepository timesheetRepo;
    private final SalaryStructureRepository salaryRepo;
    private final PayrollRepository payrollRepo;
    private final PayrollDetailRepository payrollDetailRepo;
    private final PayrollMapper payrollMapper;

    public PayrollResponse calculate(CalculatePayrollRequest req) {


//        basePerDay = baseSalary / số ngày đi làm
//
//        actualBase = basePerDay * totalWorkDays
//
//        otPay = otHours * (basePerDay / 8) * otRate
//
//        latePenalty = lateMinutes * latePenaltyPerMin
//        earlyPenalty = earlyMinutes * earlyPenaltyPerMin
//
//        grossSalary = actualBase + allowance + otPay
//        totalDeduction = latePenalty + earlyPenalty
//        netSalary = grossSalary - totalDeduction

        YearMonth ym = YearMonth.parse(req.getMonth());

        BigDecimal STANDARD_WORK_DAYS =
                calculateWorkingDays(ym.getYear(), ym.getMonthValue());


        // Check timesheet
        TimesheetMonthEntity timesheet = timesheetRepo
                .findByEmployeeIdAndMonth(req.getEmployeeId(), req.getMonth())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy bảng công"
                ));

        if (timesheet.getStatus() != TimesheetStatus.CLOSED) {
            throw new BusinessException(
                    ErrorCode.INVALID_STATE,
                    "Bảng công chưa chốt, cần chốt bảng công trước khi tính lương"
            );
        }

        // Check payroll tồn tại
        payrollRepo.findByEmployeeIdAndMonth(req.getEmployeeId(), req.getMonth())
                .ifPresent(p -> {
                    throw new BusinessException(
                            ErrorCode.DATA_ALREADY_EXISTS,
                            "Lương đã được tính toán xong"
                    );
                });

        //  Lấy salary structure
        SalaryStructureEntity salary = salaryRepo
                .findTopByEmployeeIdAndEffectiveFromLessThanEqualOrderByEffectiveFromDesc(
                        req.getEmployeeId(),
                        LocalDate.parse(req.getMonth() + "-01")
                )
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Salary structure not found"
                ));

        // ====== TÍNH TOÁN ======
        BigDecimal basePerDay = salary.getBaseSalary()
                .divide(STANDARD_WORK_DAYS, 2, RoundingMode.HALF_UP);

        BigDecimal actualBase = basePerDay
                .multiply(timesheet.getTotalWorkDays());

        BigDecimal hourlyRate = basePerDay
                .divide(BigDecimal.valueOf(8), 2, RoundingMode.HALF_UP);

        BigDecimal otPay = timesheet.getOtHours()
                .multiply(hourlyRate)
                .multiply(salary.getOtRate());

        BigDecimal latePenalty = BigDecimal.valueOf(timesheet.getLateMinutes())
                .multiply(salary.getLatePenaltyPerMin());

        BigDecimal earlyPenalty = BigDecimal.valueOf(timesheet.getEarlyMinutes())
                .multiply(salary.getEarlyPenaltyPerMin());

        BigDecimal grossSalary = actualBase
                .add(salary.getAllowance())
                .add(otPay);

        BigDecimal totalDeduction = latePenalty.add(earlyPenalty);

        BigDecimal netSalary = grossSalary.subtract(totalDeduction);

        // ====== SAVE PAYROLL ======
        PayrollEntity payroll = PayrollEntity.builder()
                .employeeId(req.getEmployeeId())
                .month(req.getMonth())
                .grossSalary(grossSalary)
                .totalDeduction(totalDeduction)
                .netSalary(netSalary)
                .status(PayrollStatus.DRAFT)
                .build();

        payrollRepo.save(payroll);

        // ====== PAYROLL DETAIL ======
        saveDetail(payroll.getId(), PayrollComponentType.BASE_SALARY, actualBase, "Base salary");
        saveDetail(payroll.getId(), PayrollComponentType.ALLOWANCE, salary.getAllowance(), "Allowance");
        saveDetail(payroll.getId(), PayrollComponentType.OT, otPay, "Overtime");
        saveDetail(payroll.getId(), PayrollComponentType.LATE_PENALTY, latePenalty.negate(), "Late penalty");
        saveDetail(payroll.getId(), PayrollComponentType.EARLY_PENALTY, earlyPenalty.negate(), "Early penalty");



        var details = payrollDetailRepo.findAllByPayrollId(payroll.getId());

        PayrollResponse response = payrollMapper.toResponse(payroll);
        response.setDetails(
                payrollMapper.toDetailResponses(details)
        );
        return response;
    }

    private void saveDetail(Long payrollId,
                            PayrollComponentType type,
                            BigDecimal amount,
                            String desc) {


        payrollDetailRepo.save(
                PayrollDetailEntity.builder()
                        .payrollId(payrollId)
                        .componentType(type)
                        .amount(amount)
                        .description(desc)
                        .build()
        );
    }


    public static BigDecimal calculateWorkingDays(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        BigDecimal workingDays = BigDecimal.ZERO;

        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);
            DayOfWeek dow = date.getDayOfWeek();

            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
                workingDays = workingDays.add(BigDecimal.ONE);
            }
        }
        return workingDays;
    }

}
