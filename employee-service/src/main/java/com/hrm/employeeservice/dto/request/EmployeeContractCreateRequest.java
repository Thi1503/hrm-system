package com.hrm.employeeservice.dto.request;

import com.hrm.employeeservice.entity.ContractStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeContractCreateRequest {

    @NotNull
    Long employeeId;

    @NotBlank
    String contractNumber;

    String contractType;

    @NotNull
    LocalDate startDate;

    LocalDate endDate;

    BigDecimal salaryIndex;

    @NotNull
    BigDecimal salaryBase;

    String position;

    String contractUrl;

    ContractStatus status;
}
