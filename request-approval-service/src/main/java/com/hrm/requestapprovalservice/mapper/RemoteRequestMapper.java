package com.hrm.requestapprovalservice.mapper;

import com.hrm.requestapprovalservice.dto.response.RemoteRequestResponse;
import com.hrm.requestapprovalservice.entity.RemoteRequestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RemoteRequestMapper {

    RemoteRequestResponse toResponse(RemoteRequestEntity entity);
}

