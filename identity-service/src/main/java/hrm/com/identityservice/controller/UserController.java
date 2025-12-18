package hrm.com.identityservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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

    /* ========= UPDATE (POST) ========= */
    @PostMapping("/{id}")
    BaseResponse<UserResponse> update(
            @PathVariable String id,
            @RequestBody UserRequest request) {
        return BaseResponse.success(userService.update(id, request));
    }

    /* ========= DELETE ========= */
    @DeleteMapping("/{id}")
    BaseResponse<Void> delete(@PathVariable("id") String id) {
        userService.delete(id);
        return BaseResponse.success(null);
    }
}
