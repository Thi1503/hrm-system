package com.hrm.attendanceservice.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AttendanceCheckOutRequest {

    @NotNull
    BigDecimal latitude;

    @NotNull
    BigDecimal longitude;

    String deviceInfo;
}

