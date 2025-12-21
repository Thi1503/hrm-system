package com.hrm.employeeservice.mapper;

import com.hrm.employeeservice.dto.request.JobPositionRequest;
import com.hrm.employeeservice.dto.response.JobPositionResponse;
import com.hrm.employeeservice.entity.JobPosition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JobPositionMapper {
    JobPosition toEntity(JobPositionRequest request);

    JobPositionResponse toResponse(JobPosition entity);

    void updateEntity(@MappingTarget JobPosition entity,
                      JobPositionRequest request);
}
