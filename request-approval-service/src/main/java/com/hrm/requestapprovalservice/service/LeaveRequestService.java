package com.hrm.requestapprovalservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.requestapprovalservice.dto.request.CreateLeaveRequest;
import com.hrm.requestapprovalservice.dto.request.UpdateLeaveRequest;
import com.hrm.requestapprovalservice.dto.response.LeaveRequestResponse;
import com.hrm.requestapprovalservice.entity.LeaveRequestEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.mapper.LeaveRequestMapper;
import com.hrm.requestapprovalservice.repository.LeaveRequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveRequestService {

    LeaveRequestRepository repository;
    LeaveRequestMapper mapper;

    /**
     * CREATE
     */
    public LeaveRequestResponse create(
            String employeeId,
            CreateLeaveRequest request) {

        Long empId = Long.valueOf(employeeId);

        boolean overlap = repository
                .existsByEmployeeIdAndStatusNotAndFromDateLessThanEqualAndToDateGreaterThanEqual(
                        empId,
                        ApprovalStatus.CANCLE,
                        request.getToDate(),
                        request.getFromDate()
                );

        if (overlap) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Khoảng ngày nghỉ đã có đơn khác"
            );
        }

        BigDecimal totalDays = calculateTotalDays(
                request.getFromDate(),
                request.getToDate()
        );

        LeaveRequestEntity entity =
                LeaveRequestEntity.builder()
                        .employeeId(empId)
                        .leaveType(request.getLeaveType())
                        .fromDate(request.getFromDate())
                        .toDate(request.getToDate())
                        .totalDays(totalDays)
                        .reason(request.getReason())
                        .attachmentUrl(request.getAttachmentUrl())
                        .status(ApprovalStatus.PENDING_MANAGER)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        return mapper.toResponse(repository.save(entity));
    }

    /**
     * UPDATE – chỉ khi PENDING_MANAGER
     */
    @Transactional
    public LeaveRequestResponse update(
            @PathVariable("id") Long id,
            String employeeId,
            UpdateLeaveRequest request) {

        LeaveRequestEntity entity = getOwnedPending(id, employeeId);

        boolean overlap = repository
                .existsByEmployeeIdAndStatusNotAndFromDateLessThanEqualAndToDateGreaterThanEqual(
                        entity.getEmployeeId(),
                        ApprovalStatus.CANCLE,
                        request.getToDate(),
                        request.getFromDate()
                );

        if (overlap &&
                (!entity.getFromDate().equals(request.getFromDate())
                        || !entity.getToDate().equals(request.getToDate()))) {

            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Khoảng ngày nghỉ đã có đơn khác"
            );
        }

        BigDecimal totalDays = calculateTotalDays(
                request.getFromDate(),
                request.getToDate()
        );

        entity.setLeaveType(request.getLeaveType());
        entity.setFromDate(request.getFromDate());
        entity.setToDate(request.getToDate());
        entity.setTotalDays(totalDays);
        entity.setReason(request.getReason());
        entity.setAttachmentUrl(request.getAttachmentUrl());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    /**
     * CANCEL – chỉ khi PENDING_MANAGER
     */
    @Transactional
    public void cancel(@PathVariable("id") Long id, String employeeId) {

        LeaveRequestEntity entity = getOwnedPending(id, employeeId);

        entity.setStatus(ApprovalStatus.CANCLE);
        entity.setUpdatedAt(LocalDateTime.now());

        repository.save(entity);
    }

    /**
     * DETAIL
     */
    public LeaveRequestResponse detail(Long id) {
        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Không tìm thấy đơn nghỉ phép"
                        ))
        );
    }

    /**
     * LIST – đơn của tôi
     */
    public List<LeaveRequestResponse> listMy(String employeeId) {
        return repository.findByEmployeeId(Long.valueOf(employeeId))
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * ===== COMMON CHECK =====
     */
    private LeaveRequestEntity getOwnedPending(
            Long id, String employeeId) {

        LeaveRequestEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy đơn nghỉ phép"
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

    private BigDecimal calculateTotalDays(LocalDate from, LocalDate to) {
        if (to.isBefore(from)) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu"
            );
        }

        long days = ChronoUnit.DAYS.between(from, to) + 1;
        return BigDecimal.valueOf(days);
    }

}
