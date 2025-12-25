package hrm.com.identityservice.service;

import java.util.List;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import hrm.com.identityservice.client.EmployeeClient;
import hrm.com.identityservice.dto.request.AccountIdRequest;
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
    EmployeeClient employeeClient;

    public LoginResponse login(LoginRequest request) {

        // 1️⃣ Find user
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.NOT_FOUND, "User không tồn tại"));

        // 2️⃣ Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Mật khẩu không chính xác"
            );
        }

        // 3️⃣ Roles
        List<String> roles = userRoleRepository.findByIdUserId(user.getId())
                .stream()
                .map(r -> r.getId().getRoleName())
                .toList();

        // 4️⃣ Call Employee Service
        var employeeResp = employeeClient.getByAccountId(
                new AccountIdRequest(user.getId())
        );

        if (!employeeResp.isSuccess() || employeeResp.getData() == null) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Không tìm thấy thông tin nhân viên"
            );
        }

        var employee = employeeResp.getData();

        // 5️⃣ Build JWT
        String token = jwtUtil.generateToken(
                user.getId(),
                user.getUsername(),
                user.getStatus().name(),
                employee.getEmployeeId(),
                employee.getFullName(),
                roles
        );

        return LoginResponse.builder()
                .accessToken(token)
                .expiresAt(System.currentTimeMillis() + 15552000000L)
                .build();
    }
}

