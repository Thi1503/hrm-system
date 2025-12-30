package com.hrm.requestapprovalservice.dto.request;

import com.hrm.requestapprovalservice.enums.RemoteWorkType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRemoteRequest {

    @NotNull
    RemoteWorkType workType;

    String reason;
}
