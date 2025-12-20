package hrm.com.identityservice.mapper;

import org.mapstruct.Mapper;

import hrm.com.identityservice.dto.request.PermissionRequest;
import hrm.com.identityservice.dto.response.PermissionResponse;
import hrm.com.identityservice.entity.PermissionEntity;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionEntity toEntity(PermissionRequest request);

    PermissionResponse toResponse(PermissionEntity entity);
}
