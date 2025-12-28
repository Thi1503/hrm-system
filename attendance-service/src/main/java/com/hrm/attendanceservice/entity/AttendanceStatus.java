package com.hrm.attendanceservice.entity;

public enum AttendanceStatus {
    NORMAL,
    LATE,
    EARLY,
    ABSENT,
    OFF,     // ngày nghỉ, không làm
    OT       // ngày nghỉ nhưng có đi làm
}
