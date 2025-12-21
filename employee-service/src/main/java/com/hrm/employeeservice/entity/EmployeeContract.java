package com.hrm.employeeservice.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "employee_contract")
@Getter @Setter
public class EmployeeContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "contract_number", nullable = false, unique = true)
    private String contractNumber;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "salary_index")
    private BigDecimal salaryIndex;

    @Column(name = "salary_base", nullable = false)
    private BigDecimal salaryBase;

    private String position;

    @Column(name = "contract_url")
    private String contractUrl;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

