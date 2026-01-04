package com.hrm.payrollservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.payrollservice.dto.request.CloseTimesheetRequest;
import com.hrm.payrollservice.entity.TimesheetMonthEntity;
import com.hrm.payrollservice.enums.TimesheetStatus;
import com.hrm.payrollservice.repository.TimesheetMonthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CloseTimesheetService {

    private final TimesheetMonthRepository monthRepo;

    public void closeEmployeeMonth(CloseTimesheetRequest request) {

        TimesheetMonthEntity monthEntity = monthRepo
                .findByEmployeeIdAndMonth(request.getEmployeeId(), request.getMonth())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Timesheet not found"
                ));

        if (monthEntity.getStatus() != TimesheetStatus.DRAFT) {
            throw new BusinessException(
                    ErrorCode.INVALID_STATE,
                    "Timesheet is not in DRAFT status"
            );
        }

        monthEntity.setStatus(TimesheetStatus.CLOSED);
        monthRepo.save(monthEntity);
    }
}
