package hrm.com.identityservice.service;

import java.util.List;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import hrm.com.identityservice.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hrm.com.identityservice.dto.request.LoginRequest;
import hrm.com.identityservice.dto.response.LoginResponse;
import hrm.com.identityservice.entity.UserEntity;
import hrm.com.identityservice.repository.UserRepository;
import hrm.com.identityservice.repository.UserRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"Mật khẩu không chính xác");
        }

        List<String> roles = userRoleRepository.findByIdUserId(user.getId())
                .stream()
                .map(r -> r.getId().getRoleName())
                .toList();

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getUsername(),
                user.getStatus().name(),
                roles
        );

        return LoginResponse.builder()
                .accessToken(token)
                .expiresAt(System.currentTimeMillis() + 15552000000L)
                .build();
    }
}
