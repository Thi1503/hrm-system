package com.hrm.attendanceservice.service;

import com.hrm.attendanceservice.dto.request.AttendanceLocationRuleRequest;
import com.hrm.attendanceservice.dto.response.AttendanceLocationRuleResponse;
import com.hrm.attendanceservice.entity.AttendanceLocationRuleEntity;
import com.hrm.attendanceservice.mapper.AttendanceLocationRuleMapper;
import com.hrm.attendanceservice.repository.AttendanceLocationRuleRepository;
import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceLocationRuleService {

    AttendanceLocationRuleRepository repository;
    AttendanceLocationRuleMapper mapper;

    /** CREATE */
    public AttendanceLocationRuleResponse create(AttendanceLocationRuleRequest request) {

        AttendanceLocationRuleEntity entity = AttendanceLocationRuleEntity.builder()
                .name(request.getName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .radiusMeter(request.getRadiusMeter())
                .isActive(request.getIsActive())
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.toResponse(repository.save(entity));
    }

    /** UPDATE */
    public AttendanceLocationRuleResponse update(Long id,
                                                 AttendanceLocationRuleRequest request) {

        AttendanceLocationRuleEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy cấu hình vị trí"
                ));

        entity.setName(request.getName());
        entity.setLatitude(request.getLatitude());
        entity.setLongitude(request.getLongitude());
        entity.setRadiusMeter(request.getRadiusMeter());
        entity.setIsActive(request.getIsActive());

        return mapper.toResponse(repository.save(entity));
    }

    /** DETAIL */
    public AttendanceLocationRuleResponse getDetail(Long id) {
        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Không tìm thấy cấu hình vị trí"
                        ))
        );
    }

    /** LIST */
    public List<AttendanceLocationRuleResponse> getList() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /** DELETE */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Không tìm thấy cấu hình vị trí"
            );
        }
        repository.deleteById(id);
    }
}
