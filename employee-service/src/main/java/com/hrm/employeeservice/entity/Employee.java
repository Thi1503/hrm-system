package com.hrm.employeeservice.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "employee")
@Getter @Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Map sang identity-service */
    @Column(name = "account_id", nullable = false, length = 36)
    private String accountId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "citizen_id")
    private String citizenId;

    @Column(name = "issued_date")
    private LocalDate issuedDate;

    @Column(name = "issued_place")
    private String issuedPlace;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;
    private String address;

    /** ========== RELATION ========== */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private JobPosition position;

    /** Quản lý trực tiếp */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "start_work_date", nullable = false)
    private LocalDate startWorkDate;

    @Column(name = "end_work_date")
    private LocalDate endWorkDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status")
    private EmploymentStatus employmentStatus;

    @Column(name = "salary_basic")
    private BigDecimal salaryBasic;

    @Column(name = "extra_info", columnDefinition = "TEXT")
    private String extraInfo;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
