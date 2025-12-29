package com.hrm.requestapprovalservice.mapper;

import com.hrm.requestapprovalservice.dto.response.LeaveRequestResponse;
import com.hrm.requestapprovalservice.entity.LeaveRequestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapper {

    LeaveRequestResponse toResponse(LeaveRequestEntity entity);
}
