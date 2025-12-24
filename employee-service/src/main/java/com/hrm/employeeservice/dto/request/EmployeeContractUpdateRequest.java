package com.hrm.employeeservice.dto.request;

import com.hrm.employeeservice.entity.ContractStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeContractUpdateRequest {

    String contractType;

    LocalDate startDate;
    LocalDate endDate;

    BigDecimal salaryIndex;
    BigDecimal salaryBase;

    String position;
    String contractUrl;

    ContractStatus status;
}
