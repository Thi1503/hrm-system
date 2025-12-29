package com.hrm.attendanceservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignShiftRequest {

    Long employeeId;
    Long shiftId;
    LocalDate fromDate;
    LocalDate toDate;
}
