package com.hrm.requestapprovalservice.dto.request;

import com.hrm.requestapprovalservice.enums.RequestType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RejectApprovalRequest {

    private RequestType requestType;
    private Long requestId;
    private String comment;
}
