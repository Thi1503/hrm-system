package com.hrm.requestapprovalservice.mapper.hr;

import com.hrm.requestapprovalservice.dto.response.hr.HrOtApprovalResponse;
import com.hrm.requestapprovalservice.entity.OtRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HrOtApprovalMapper {

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "employeeName", ignore = true)
    @Mapping(target = "managerId", ignore = true)
    @Mapping(target = "managerName", ignore = true)
    @Mapping(target = "departmentId", ignore = true)
    @Mapping(target = "departmentName", ignore = true)
    @Mapping(target = "positionId", ignore = true)
    @Mapping(target = "positionName", ignore = true)
    HrOtApprovalResponse toResponse(OtRequestEntity entity);
}
