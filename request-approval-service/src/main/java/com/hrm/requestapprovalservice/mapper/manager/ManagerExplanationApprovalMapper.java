package com.hrm.requestapprovalservice.mapper.manager;

import com.hrm.requestapprovalservice.dto.response.manager.ManagerExplanationApprovalResponse;
import com.hrm.requestapprovalservice.entity.AttendanceExplanationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerExplanationApprovalMapper {

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "employeeName", ignore = true) // set sau
    ManagerExplanationApprovalResponse toResponse(
            AttendanceExplanationEntity entity
    );
}
