package com.hrm.requestapprovalservice.mapper;

import com.hrm.requestapprovalservice.dto.response.AttendanceExplanationResponse;
import com.hrm.requestapprovalservice.entity.AttendanceExplanationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceExplanationMapper {

    AttendanceExplanationResponse toResponse(AttendanceExplanationEntity entity);
}
