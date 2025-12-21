package com.hrm.employeeservice.mapper;

import com.hrm.employeeservice.dto.request.DepartmentRequest;
import com.hrm.employeeservice.dto.response.DepartmentResponse;
import com.hrm.employeeservice.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

  Department toEntity(DepartmentRequest request);

  DepartmentResponse toResponse(Department entity);

  void updateEntity(@MappingTarget Department entity,
                    DepartmentRequest request);
}
