package com.hrm.attendanceservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceApproveLocationRequest {

    Long attendanceLogId;
    Boolean approve;
    String note;
}

