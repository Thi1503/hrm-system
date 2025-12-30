package com.hrm.requestapprovalservice.mapper;

import com.hrm.requestapprovalservice.dto.response.OtRequestResponse;
import com.hrm.requestapprovalservice.entity.OtRequestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtRequestMapper {

    OtRequestResponse toResponse(OtRequestEntity entity);
}

