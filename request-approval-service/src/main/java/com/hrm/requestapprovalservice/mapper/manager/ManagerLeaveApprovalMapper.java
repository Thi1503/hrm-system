package com.hrm.requestapprovalservice.mapper.manager;

import com.hrm.requestapprovalservice.dto.response.manager.ManagerLeaveApprovalResponse;
import com.hrm.requestapprovalservice.entity.LeaveRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerLeaveApprovalMapper {

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "employeeName", ignore = true)
    ManagerLeaveApprovalResponse toResponse(LeaveRequestEntity entity);
}
