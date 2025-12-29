package com.hrm.requestapprovalservice.dto.request;

import com.hrm.requestapprovalservice.enums.ExplanationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAttendanceExplanationRequest {

    @NotNull
    LocalDate workDate;

    @NotNull
    ExplanationType explanationType;

    @NotNull
    String reason;

    String attachmentUrl;
}
