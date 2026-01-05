package com.hrm.payrollservice.service;

import com.hrm.common.exception.BusinessException;
import com.hrm.common.enums.ErrorCode;
import com.hrm.payrollservice.dto.request.UpdateSalaryStructureRequest;
import com.hrm.payrollservice.dto.response.SalaryStructureResponse;
import com.hrm.payrollservice.entity.SalaryStructureEntity;
import com.hrm.payrollservice.repository.SalaryStructureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryStructureService {

    private final SalaryStructureRepository salaryRepo;

    public void updateSalary(UpdateSalaryStructureRequest request) {

        if (request.getBaseSalary() == null
                || request.getEffectiveFrom() == null) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Base salary and effectiveFrom are required"
            );
        }

        SalaryStructureEntity entity = SalaryStructureEntity.builder()
                .employeeId(request.getEmployeeId())
                .baseSalary(request.getBaseSalary())
                .allowance(
                        request.getAllowance() == null
                                ? BigDecimal.ZERO
                                : request.getAllowance()
                )
                .otRate(
                        request.getOtRate() == null
                                ? BigDecimal.valueOf(1.5)
                                : request.getOtRate()
                )
                .latePenaltyPerMin(
                        request.getLatePenaltyPerMin() == null
                                ? BigDecimal.ZERO
                                : request.getLatePenaltyPerMin()
                )
                .earlyPenaltyPerMin(
                        request.getEarlyPenaltyPerMin() == null
                                ? BigDecimal.ZERO
                                : request.getEarlyPenaltyPerMin()
                )
                .effectiveFrom(request.getEffectiveFrom())
                .build();

        salaryRepo.save(entity);
    }


    public SalaryStructureResponse getCurrentSalary(Long employeeId) {

        SalaryStructureEntity entity = salaryRepo
                .findTopByEmployeeIdAndEffectiveFromLessThanEqualOrderByEffectiveFromDesc(
                        employeeId, LocalDate.now()
                )
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Salary structure not found"
                ));

        return SalaryStructureResponse.builder()
                .employeeId(entity.getEmployeeId())
                .baseSalary(entity.getBaseSalary())
                .allowance(entity.getAllowance())
                .otRate(entity.getOtRate())
                .latePenaltyPerMin(entity.getLatePenaltyPerMin())
                .earlyPenaltyPerMin(entity.getEarlyPenaltyPerMin())
                .effectiveFrom(entity.getEffectiveFrom())
                .build();
    }
}
