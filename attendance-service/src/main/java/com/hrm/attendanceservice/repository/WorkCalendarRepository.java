package com.hrm.attendanceservice.repository;

import com.hrm.attendanceservice.entity.WorkCalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkCalendarRepository
        extends JpaRepository<WorkCalendarEntity, Long> {

    Optional<WorkCalendarEntity> findByWorkDate(LocalDate workDate);
}
