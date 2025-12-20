package hrm.com.identityservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import hrm.com.identityservice.dto.request.PermissionRequest;
import hrm.com.identityservice.dto.response.PermissionResponse;
import hrm.com.identityservice.service.PermissionService;
import com.hrm.common.response.BaseResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    BaseResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return BaseResponse.success(permissionService.create(request));
    }

    @GetMapping
    BaseResponse<List<PermissionResponse>> getAll() {
        return BaseResponse.success(permissionService.getAll());
    }

    @DeleteMapping("/{permission}")
    BaseResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return BaseResponse.success(null);
    }
}
