package com.hrm.employeeservice.dto.response;

import com.hrm.employeeservice.entity.ContractStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeContractResponse {

    Long contractId;

    Long employeeId;
    String employeeName;

    String contractNumber;
    String contractType;

    LocalDate startDate;
    LocalDate endDate;

    BigDecimal salaryIndex;
    BigDecimal salaryBase;

    String position;
    String contractUrl;

    ContractStatus status;

    LocalDateTime createdAt;
}
