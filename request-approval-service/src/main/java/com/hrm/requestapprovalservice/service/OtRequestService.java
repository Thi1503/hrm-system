package com.hrm.requestapprovalservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.requestapprovalservice.dto.request.CreateOtRequest;
import com.hrm.requestapprovalservice.dto.request.UpdateOtRequest;
import com.hrm.requestapprovalservice.dto.response.OtRequestResponse;
import com.hrm.requestapprovalservice.entity.OtRequestEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.mapper.OtRequestMapper;
import com.hrm.requestapprovalservice.repository.OtRequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtRequestService {

    OtRequestRepository repository;
    OtRequestMapper mapper;

    /** CREATE */
    public OtRequestResponse create(
            String employeeId,
            CreateOtRequest request) {

        Long empId = Long.valueOf(employeeId);

        boolean existed = repository
                .existsByEmployeeIdAndOtDateAndStatusNot(
                        empId,
                        request.getOtDate(),
                        ApprovalStatus.CANCLE
                );

        if (existed) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Ngày này đã có đơn OT"
            );
        }

        BigDecimal totalHours = calculateTotalHours(
                request.getStartTime(),
                request.getEndTime()
        );

        OtRequestEntity entity =
                OtRequestEntity.builder()
                        .employeeId(empId)
                        .otDate(request.getOtDate())
                        .startTime(request.getStartTime())
                        .endTime(request.getEndTime())
                        .totalHours(totalHours)
                        .reason(request.getReason())
                        .status(ApprovalStatus.PENDING_MANAGER)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        return mapper.toResponse(repository.save(entity));
    }

    /** UPDATE – chỉ khi PENDING_MANAGER */
    @Transactional
    public OtRequestResponse update(
            @PathVariable("id") Long id,
            String employeeId,
            UpdateOtRequest request) {

        OtRequestEntity entity = getOwnedPending(id, employeeId);
        BigDecimal totalHours = calculateTotalHours(
                request.getStartTime(),
                request.getEndTime()
        );
        entity.setStartTime(request.getStartTime());
        entity.setEndTime(request.getEndTime());
        entity.setTotalHours(totalHours);
        entity.setReason(request.getReason());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    /** CANCEL – chỉ khi PENDING_MANAGER */
    @Transactional
    public void cancel(@PathVariable("id") Long id, String employeeId) {

        OtRequestEntity entity = getOwnedPending(id, employeeId);

        entity.setStatus(ApprovalStatus.CANCLE);
        entity.setUpdatedAt(LocalDateTime.now());

        repository.save(entity);
    }

    /** DETAIL */
    public OtRequestResponse detail(Long id) {
        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Không tìm thấy đơn OT"
                        ))
        );
    }

    /** LIST – đơn của tôi */
    public List<OtRequestResponse> listMy(String employeeId) {
        return repository.findByEmployeeId(Long.valueOf(employeeId))
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /** ===== COMMON CHECK ===== */
    private OtRequestEntity getOwnedPending(
            Long id, String employeeId) {

        OtRequestEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy đơn OT"
                ));

        if (!entity.getEmployeeId().equals(Long.valueOf(employeeId))) {
            throw new BusinessException(
                    ErrorCode.FORBIDDEN,
                    "Không có quyền thao tác đơn này"
            );
        }

        if (entity.getStatus() != ApprovalStatus.PENDING_MANAGER) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Chỉ được thao tác khi đang chờ quản lý duyệt"
            );
        }

        return entity;
    }

    private BigDecimal calculateTotalHours(LocalTime start, LocalTime end) {

        if (!end.isAfter(start)) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Giờ kết thúc phải sau giờ bắt đầu"
            );
        }

        long minutes = Duration.between(start, end).toMinutes();

        // MVP: làm tròn 2 chữ số thập phân
        return BigDecimal.valueOf(minutes)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
    }

}
