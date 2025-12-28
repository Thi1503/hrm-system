package com.hrm.attendanceservice.mapper;

import com.hrm.attendanceservice.dto.response.AttendanceShiftResponse;
import com.hrm.attendanceservice.entity.AttendanceShiftEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceShiftMapper {

    AttendanceShiftResponse toResponse(AttendanceShiftEntity entity);
}
