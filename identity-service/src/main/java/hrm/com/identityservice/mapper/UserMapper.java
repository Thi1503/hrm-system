package hrm.com.identityservice.mapper;

import org.mapstruct.Mapper;

import hrm.com.identityservice.dto.request.UserRequest;
import hrm.com.identityservice.dto.response.UserResponse;
import hrm.com.identityservice.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRequest request);

    UserResponse toResponse(UserEntity entity);
}
