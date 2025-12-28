package com.hrm.attendanceservice.entity;

import com.hrm.attendanceservice.entity.AttendanceCheckType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Column(name = "check_time", nullable = false)
    LocalDateTime checkTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "check_type", nullable = false)
    AttendanceCheckType checkType;

    @Column(nullable = false, precision = 10, scale = 6)
    BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 6)
    BigDecimal longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_rule_id")
    AttendanceLocationRuleEntity locationRule;

    @Column(name = "is_valid_location", nullable = false)
    Boolean isValidLocation;

    @Column(name = "device_info")
    String deviceInfo;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
