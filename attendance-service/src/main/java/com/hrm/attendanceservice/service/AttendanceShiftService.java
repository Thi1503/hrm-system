package com.hrm.attendanceservice.service;

import com.hrm.attendanceservice.dto.request.AttendanceShiftRequest;
import com.hrm.attendanceservice.dto.response.AttendanceShiftResponse;
import com.hrm.attendanceservice.entity.AttendanceShiftEntity;
import com.hrm.attendanceservice.mapper.AttendanceShiftMapper;
import com.hrm.attendanceservice.repository.AttendanceShiftRepository;
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
public class AttendanceShiftService {

    AttendanceShiftRepository repository;
    AttendanceShiftMapper mapper;

    /** CREATE */
    public AttendanceShiftResponse create(AttendanceShiftRequest request) {

        AttendanceShiftEntity entity = AttendanceShiftEntity.builder()
                .name(request.getName())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .lateThresholdMin(request.getLateThresholdMin())
                .earlyThresholdMin(request.getEarlyThresholdMin())
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.toResponse(repository.save(entity));
    }

    /** UPDATE */
    public AttendanceShiftResponse update(Long id,
                                          AttendanceShiftRequest request) {

        AttendanceShiftEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy ca làm việc"
                ));

        entity.setName(request.getName());
        entity.setStartTime(request.getStartTime());
        entity.setEndTime(request.getEndTime());
        entity.setLateThresholdMin(request.getLateThresholdMin());
        entity.setEarlyThresholdMin(request.getEarlyThresholdMin());

        return mapper.toResponse(repository.save(entity));
    }

    /** DETAIL */
    public AttendanceShiftResponse getDetail(Long id) {
        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Không tìm thấy ca làm việc"
                        ))
        );
    }

    /** LIST */
    public List<AttendanceShiftResponse> getList() {
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
                    "Không tìm thấy ca làm việc"
            );
        }
        repository.deleteById(id);
    }
}
