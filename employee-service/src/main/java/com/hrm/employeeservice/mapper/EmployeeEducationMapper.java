package com.hrm.employeeservice.mapper;

import com.hrm.employeeservice.dto.response.EmployeeEducationResponse;
import com.hrm.employeeservice.entity.EmployeeEducation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEducationMapper {

    EmployeeEducationResponse toResponse(EmployeeEducation entity);
}
