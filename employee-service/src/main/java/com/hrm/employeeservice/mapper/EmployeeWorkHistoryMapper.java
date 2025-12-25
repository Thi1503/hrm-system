package com.hrm.employeeservice.mapper;

import com.hrm.employeeservice.dto.response.EmployeeWorkHistoryResponse;
import com.hrm.employeeservice.entity.EmployeeWorkHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeWorkHistoryMapper {

    EmployeeWorkHistoryResponse toResponse(EmployeeWorkHistory entity);
}
