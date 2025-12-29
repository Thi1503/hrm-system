package com.hrm.attendanceservice.job;

import com.hrm.attendanceservice.entity.AttendanceDailySummaryEntity;
import com.hrm.attendanceservice.entity.AttendanceStatus;
import com.hrm.attendanceservice.entity.DayType;
import com.hrm.attendanceservice.entity.WorkCalendarEntity;
import com.hrm.attendanceservice.repository.AttendanceDailySummaryRepository;
import com.hrm.attendanceservice.repository.WorkCalendarRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceAbsentJob {

    AttendanceDailySummaryRepository summaryRepository;
    WorkCalendarRepository calendarRepository;

    @Scheduled(cron = "0 5 23 * * ?")
    @Transactional
    public void markAbsent() {

        LocalDate today = LocalDate.now();

        DayType dayType = calendarRepository.findByWorkDate(today)
                .map(WorkCalendarEntity::getDayType)
                .orElse(DayType.WORKING);

        if (dayType != DayType.WORKING) {
            return;
        }

        List<AttendanceDailySummaryEntity> summaries =
                summaryRepository.findAllByWorkDate(today);

        for (AttendanceDailySummaryEntity s : summaries) {

            if (s.getCheckInTime() == null) {
                s.setStatus(AttendanceStatus.ABSENT);
                summaryRepository.save(s);
            }
        }
    }
}

