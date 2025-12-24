package com.hrm.employeeservice.mapper;

import com.hrm.employeeservice.dto.response.EmployeeContractResponse;
import com.hrm.employeeservice.entity.EmployeeContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeContractMapper {

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee.fullName")
    EmployeeContractResponse toResponse(EmployeeContract entity);
}
