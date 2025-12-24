package com.hrm.employeeservice.mapper;

import com.hrm.employeeservice.dto.response.EmployeeRelationshipResponse;
import com.hrm.employeeservice.entity.EmployeeRelationship;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeRelationshipMapper {

    EmployeeRelationshipResponse toResponse(EmployeeRelationship entity);
}
