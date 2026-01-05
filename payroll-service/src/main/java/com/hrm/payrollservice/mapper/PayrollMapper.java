package com.hrm.payrollservice.mapper;

import com.hrm.payrollservice.dto.response.PayrollDetailResponse;
import com.hrm.payrollservice.dto.response.PayrollResponse;
import com.hrm.payrollservice.entity.PayrollDetailEntity;
import com.hrm.payrollservice.entity.PayrollEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PayrollMapper {

    // ===== Payroll =====
    @Mapping(target = "payrollId", source = "id")
    PayrollResponse toResponse(PayrollEntity entity);

    // ===== Payroll Detail =====
    PayrollDetailResponse toDetailResponse(PayrollDetailEntity entity);

    List<PayrollDetailResponse> toDetailResponses(List<PayrollDetailEntity> entities);
}
