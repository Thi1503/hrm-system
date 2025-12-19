package hrm.com.identityservice.controller;

import java.util.List;

import hrm.com.identityservice.dto.request.ChangePasswordRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import hrm.com.identityservice.security.JwtUtil;

import hrm.com.identityservice.dto.request.UserRequest;
import hrm.com.identityservice.dto.response.UserResponse;
import hrm.com.identityservice.service.UserService;
import com.hrm.common.response.BaseResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    JwtUtil jwtUtil;

    /* ========= CREATE ========= */
    @PostMapping
    BaseResponse<UserResponse> create(@RequestBody UserRequest request) {
        return BaseResponse.success(userService.create(request));
    }

    /* ========= GET ALL ========= */
    @GetMapping
    BaseResponse<List<UserResponse>> getAll() {
        return BaseResponse.success(userService.getAll());
    }

    /* ========= GET ONE ========= */
    @GetMapping("/{id}")
    BaseResponse<UserResponse> getById(@PathVariable("id") String id) {
        return BaseResponse.success(userService.getById(id));
    }

    @PostMapping("/{id}")
    BaseResponse<UserResponse> update(
            @PathVariable("id") String id,
            @RequestBody UserRequest request) {
        return BaseResponse.success(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    BaseResponse<Void> delete(@PathVariable("id") String id) {
        userService.delete(id);
        return BaseResponse.success(null);
    }

    /* ========= CHANGE PASSWORD (JWT) ========= */
    @PostMapping("/change-password")
    BaseResponse<Void> changePassword(
            HttpServletRequest request,
            @RequestBody ChangePasswordRequest body) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("TOKEN_MISSING");
        }

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserIdFromToken(token);

        userService.changePasswordByUserId(userId, body);
        return BaseResponse.success(null);
    }

}
