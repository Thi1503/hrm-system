package hrm.com.identityservice.service;

import java.time.LocalDateTime;
import java.util.List;


import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import hrm.com.identityservice.entity.UserStatus;
import org.springframework.stereotype.Service;

import hrm.com.identityservice.dto.request.UserRequest;
import hrm.com.identityservice.dto.response.UserResponse;
import hrm.com.identityservice.entity.UserEntity;
import hrm.com.identityservice.mapper.UserMapper;
import hrm.com.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    /* ================= CREATE ================= */
    public UserResponse create(UserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) throw new BusinessException(ErrorCode.DATA_ALREADY_EXISTS, "User is Already Exists");
        UserEntity user = userMapper.toEntity(request);
        user.setId(java.util.UUID.randomUUID().toString());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userMapper.toResponse(userRepository.save(user));
    }

    /* ================= GET ALL ================= */
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    /* ================= GET ONE ================= */
    public UserResponse getById(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
        return userMapper.toResponse(user);
    }

    /* ================= UPDATE (POST) ================= */
    public UserResponse update(String id, UserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }


        user.setUpdatedAt(LocalDateTime.now());
        return userMapper.toResponse(userRepository.save(user));
    }

    /* ================= DELETE ================= */
    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
