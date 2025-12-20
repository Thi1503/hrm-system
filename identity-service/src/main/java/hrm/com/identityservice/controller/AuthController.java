package hrm.com.identityservice.controller;

import org.springframework.web.bind.annotation.*;

import hrm.com.identityservice.dto.request.LoginRequest;
import hrm.com.identityservice.dto.response.LoginResponse;
import hrm.com.identityservice.service.AuthService;
import com.hrm.common.response.BaseResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return BaseResponse.success(authService.login(request));
    }
}
