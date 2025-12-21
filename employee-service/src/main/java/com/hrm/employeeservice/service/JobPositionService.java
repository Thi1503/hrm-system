package com.hrm.employeeservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.employeeservice.dto.request.JobPositionRequest;
import com.hrm.employeeservice.dto.response.JobPositionResponse;
import com.hrm.employeeservice.entity.JobPosition;
import com.hrm.employeeservice.mapper.JobPositionMapper;
import com.hrm.employeeservice.repository.JobPositionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPositionService {

    JobPositionRepository jobPositionRepository;
    JobPositionMapper jobPositionMapper;

    /** CREATE */
    public JobPositionResponse create(JobPositionRequest request) {
        if (jobPositionRepository.existsByName(request.getName())) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Tên chức vụ đã tồn tại"
            );
        }

        JobPosition position = jobPositionMapper.toEntity(request);
        position.setCreatedAt(LocalDateTime.now());

        return jobPositionMapper.toResponse(
                jobPositionRepository.save(position)
        );
    }

    /** UPDATE */
    public JobPositionResponse update(Long id, JobPositionRequest request) {
        JobPosition position = jobPositionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy chức vụ"
                ));

        if (!position.getName().equals(request.getName())
                && jobPositionRepository.existsByName(request.getName())) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Tên chức vụ đã tồn tại"
            );
        }

        position.setName(request.getName());
        position.setLevel(request.getLevel());

        return jobPositionMapper.toResponse(
                jobPositionRepository.save(position)
        );
    }

    /** GET DETAIL */
    public JobPositionResponse getDetail(Long id) {
        JobPosition position = jobPositionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy chức vụ"
                ));

        return jobPositionMapper.toResponse(position);
    }

    /** GET LIST */
    public List<JobPositionResponse> getList() {
        return jobPositionRepository.findAll()
                .stream()
                .map(jobPositionMapper::toResponse)
                .toList();
    }

    /** DELETE */
    public void delete(Long id) {
        if (!jobPositionRepository.existsById(id)) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Không tìm thấy chức vụ"
            );
        }
        jobPositionRepository.deleteById(id);
    }
}
