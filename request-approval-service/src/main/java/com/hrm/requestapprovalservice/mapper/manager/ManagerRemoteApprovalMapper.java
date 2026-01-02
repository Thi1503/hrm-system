package com.hrm.requestapprovalservice.mapper.manager;
import com.hrm.requestapprovalservice.dto.response.manager.ManagerRemoteApprovalResponse;
import com.hrm.requestapprovalservice.entity.RemoteRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerRemoteApprovalMapper {

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "employeeName", ignore = true)
    ManagerRemoteApprovalResponse toResponse(RemoteRequestEntity entity);
}

