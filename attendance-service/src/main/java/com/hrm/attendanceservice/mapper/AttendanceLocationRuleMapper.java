package com.hrm.attendanceservice.mapper;

import com.hrm.attendanceservice.dto.response.AttendanceLocationRuleResponse;
import com.hrm.attendanceservice.entity.AttendanceLocationRuleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceLocationRuleMapper {

    AttendanceLocationRuleResponse toResponse(AttendanceLocationRuleEntity entity);
}
