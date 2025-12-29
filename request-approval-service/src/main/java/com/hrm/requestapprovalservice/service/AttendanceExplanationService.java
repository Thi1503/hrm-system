package com.hrm.requestapprovalservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.requestapprovalservice.dto.request.CreateAttendanceExplanationRequest;
import com.hrm.requestapprovalservice.dto.request.UpdateAttendanceExplanationRequest;
import com.hrm.requestapprovalservice.dto.response.AttendanceExplanationResponse;
import com.hrm.requestapprovalservice.entity.AttendanceExplanationEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.mapper.AttendanceExplanationMapper;
import com.hrm.requestapprovalservice.repository.AttendanceExplanationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceExplanationService {

    AttendanceExplanationRepository repository;
    AttendanceExplanationMapper mapper;

    /** CREATE */
    public AttendanceExplanationResponse create(
            String employeeId,
            CreateAttendanceExplanationRequest request) {

        Long empId = Long.valueOf(employeeId);

        boolean existed = repository
                .existsByEmployeeIdAndWorkDateAndStatusNot(
                        empId,
                        request.getWorkDate(),
                        ApprovalStatus.CANCLE
                );

        if (existed) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Ngày này đã có đơn giải trình"
            );
        }

        AttendanceExplanationEntity entity =
                AttendanceExplanationEntity.builder()
                        .employeeId(empId)
                        .workDate(request.getWorkDate())
                        .explanationType(request.getExplanationType())
                        .reason(request.getReason())
                        .attachmentUrl(request.getAttachmentUrl())
                        .status(ApprovalStatus.PENDING_MANAGER)
                        .build();

        return mapper.toResponse(repository.save(entity));
    }


    /** UPDATE – chỉ khi PENDING_MANAGER */
    @Transactional
    public AttendanceExplanationResponse update(
            Long id,
            String employeeId,
            UpdateAttendanceExplanationRequest request) {

        AttendanceExplanationEntity entity = getOwnedPending(id, employeeId);

        entity.setExplanationType(request.getExplanationType());
        entity.setReason(request.getReason());
        entity.setAttachmentUrl(request.getAttachmentUrl());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    /** CANCEL – chỉ khi PENDING_MANAGER */
    @Transactional
    public void cancel(Long id, String employeeId) {

        AttendanceExplanationEntity entity = getOwnedPending(id, employeeId);

        entity.setStatus(ApprovalStatus.CANCLE);
        entity.setUpdatedAt(LocalDateTime.now());

        repository.save(entity);
    }

    /** DETAIL */
    public AttendanceExplanationResponse detail(Long id) {
        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Không tìm thấy đơn giải trình"
                        ))
        );
    }

    /** LIST – đơn của tôi */
    public List<AttendanceExplanationResponse> listMy(String employeeId) {
        return repository.findByEmployeeId(Long.valueOf(employeeId))
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /** ===== COMMON CHECK ===== */
    private AttendanceExplanationEntity getOwnedPending(
            Long id, String employeeId) {

        AttendanceExplanationEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy đơn giải trình"
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
}
