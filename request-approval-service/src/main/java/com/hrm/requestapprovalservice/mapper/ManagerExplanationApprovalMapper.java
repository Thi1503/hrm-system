package com.hrm.requestapprovalservice.mapper;

import com.hrm.requestapprovalservice.dto.response.ManagerExplanationApprovalResponse;
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
