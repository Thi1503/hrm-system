package hrm.com.identityservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hrm.com.identityservice.dto.request.PermissionRequest;
import hrm.com.identityservice.dto.response.PermissionResponse;
import hrm.com.identityservice.entity.PermissionEntity;
import hrm.com.identityservice.mapper.PermissionMapper;
import hrm.com.identityservice.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        PermissionEntity permission = permissionMapper.toEntity(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toResponse)
                .toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
