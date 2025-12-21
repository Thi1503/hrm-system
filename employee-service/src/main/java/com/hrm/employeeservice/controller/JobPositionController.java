package com.hrm.employeeservice.controller;

import com.hrm.common.response.BaseResponse;
import com.hrm.employeeservice.dto.request.JobPositionRequest;
import com.hrm.employeeservice.dto.response.JobPositionResponse;
import com.hrm.employeeservice.service.JobPositionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "job-positions")
@RestController
@RequestMapping("/job-positions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPositionController {

    JobPositionService jobPositionService;

    /** CREATE */
    @PostMapping("/create")
    BaseResponse<JobPositionResponse> create(
            @RequestBody @Valid JobPositionRequest request) {
        return BaseResponse.success(jobPositionService.create(request));
    }

    /** UPDATE */
    @PostMapping("/update/{id}")
    BaseResponse<JobPositionResponse> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid JobPositionRequest request) {
        return BaseResponse.success(jobPositionService.update(id, request));
    }

    /** GET DETAIL */
    @GetMapping("/detail/{id}")
    BaseResponse<JobPositionResponse> detail(@PathVariable("id") Long id) {
        return BaseResponse.success(jobPositionService.getDetail(id));
    }

    /** GET LIST */
    @GetMapping("/list")
    BaseResponse<List<JobPositionResponse>> list() {
        return BaseResponse.success(jobPositionService.getList());
    }

    /** DELETE */
    @DeleteMapping("/delete/{id}")
    BaseResponse<Void> delete(@PathVariable("id") Long id) {
        jobPositionService.delete(id);
        return BaseResponse.success(null);
    }
}
