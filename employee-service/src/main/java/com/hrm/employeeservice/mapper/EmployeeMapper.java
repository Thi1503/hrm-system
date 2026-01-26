package com.hrm.employeeservice.mapper;

import com.hrm.employeeservice.dto.response.EmployeeItemResponse;
import com.hrm.employeeservice.dto.response.EmployeeMyInfoResponse;
import com.hrm.employeeservice.dto.response.EmployeeResponse;
import com.hrm.employeeservice.dto.response.internalResponse.EmployeeInternalItemResponse;
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

    // ===== INTERNAL API =====
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "positionName", source = "position.name")
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "managerName", source = "manager.fullName")
    EmployeeInternalItemResponse toInternalItem(Employee employee);

    /* ===== MY INFO ===== */
    @Mapping(target = "jobPosition", source = "position.name")
    @Mapping(target = "gender", expression = "java(employee.getGender() != null ? employee.getGender().name() : null)")
    @Mapping(target = "idNumber", source = "citizenId")
    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "personalEmail", source = "email")
    @Mapping(target = "employeeCode", source = "code")
    @Mapping(target = "department", source = "department.name")
    @Mapping(target = "position", source = "position.name")
    @Mapping(target = "joinDate", source = "startWorkDate")
    @Mapping(target = "companyEmail", source = "email") // nếu có field riêng thì đổi
    @Mapping(target = "seniority", ignore = true) // set ở service
    EmployeeMyInfoResponse toMyInfo(Employee employee);
}