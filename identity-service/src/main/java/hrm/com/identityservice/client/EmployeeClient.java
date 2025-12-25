package hrm.com.identityservice.client;

import com.hrm.common.response.BaseResponse;
import hrm.com.identityservice.dto.request.AccountIdRequest;
import hrm.com.identityservice.dto.response.EmployeeInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "employee-service",
        url = "${employee.service.url}"
)
public interface EmployeeClient {
    @PostMapping("/employees/internal/by-account-id")
    BaseResponse<EmployeeInfoResponse> getByAccountId(
            @RequestBody AccountIdRequest request
    );
}
