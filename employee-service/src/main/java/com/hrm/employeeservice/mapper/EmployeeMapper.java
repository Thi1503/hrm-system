package com.hrm.employeeservice.mapper;

import com.hrm.employeeservice.dto.response.EmployeeItemResponse;
import com.hrm.employeeservice.dto.response.EmployeeResponse;
import com.hrm.employeeservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "positionName", source = "position.name")
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "managerName", source = "manager.fullName")
    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "positionName", source = "position.name")
    @Mapping(target = "departmentName", source = "department.name")
    EmployeeItemResponse toItemResponse(Employee employee);
}