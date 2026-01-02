package com.hrm.requestapprovalservice.mapper;

import com.hrm.requestapprovalservice.dto.response.ManagerOtApprovalResponse;
import com.hrm.requestapprovalservice.entity.OtRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerOtApprovalMapper {

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "employeeName", ignore = true)
    ManagerOtApprovalResponse toResponse(OtRequestEntity entity);
}

