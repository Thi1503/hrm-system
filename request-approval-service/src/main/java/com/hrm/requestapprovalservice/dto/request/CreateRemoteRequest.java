package com.hrm.requestapprovalservice.dto.request;

import com.hrm.requestapprovalservice.enums.RemoteWorkType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRemoteRequest {

    @NotNull
    LocalDate remoteDate;

    @NotNull
    RemoteWorkType workType;

    String reason;
}

