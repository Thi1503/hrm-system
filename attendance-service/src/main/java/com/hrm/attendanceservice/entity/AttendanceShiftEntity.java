package com.hrm.attendanceservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "attendance_shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(name = "start_time", nullable = false)
    LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    LocalTime endTime;

    @Column(name = "late_threshold_min", nullable = false)
    Integer lateThresholdMin;

    @Column(name = "early_threshold_min", nullable = false)
    Integer earlyThresholdMin;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;
}
