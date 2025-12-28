package com.hrm.attendanceservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_location_rule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceLocationRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, precision = 10, scale = 6)
    BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 6)
    BigDecimal longitude;

    @Column(name = "radius_meter", nullable = false)
    Integer radiusMeter;

    @Column(name = "is_active", nullable = false)
    Boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;
}
