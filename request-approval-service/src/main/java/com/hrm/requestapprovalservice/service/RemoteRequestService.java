package com.hrm.requestapprovalservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.requestapprovalservice.dto.request.CreateRemoteRequest;
import com.hrm.requestapprovalservice.dto.request.UpdateRemoteRequest;
import com.hrm.requestapprovalservice.dto.response.RemoteRequestResponse;
import com.hrm.requestapprovalservice.entity.RemoteRequestEntity;
import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.mapper.RemoteRequestMapper;
import com.hrm.requestapprovalservice.repository.RemoteRequestRepository;
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
public class RemoteRequestService {

    RemoteRequestRepository repository;
    RemoteRequestMapper mapper;

    /** CREATE */
    public RemoteRequestResponse create(
            String employeeId,
            CreateRemoteRequest request) {

        Long empId = Long.valueOf(employeeId);

        boolean existed = repository
                .existsByEmployeeIdAndRemoteDateAndStatusNot(
                        empId,
                        request.getRemoteDate(),
                        ApprovalStatus.CANCLE
                );

        if (existed) {
            throw new BusinessException(
                    ErrorCode.DATA_ALREADY_EXISTS,
                    "Ngày này đã có đơn remote"
            );
        }

        RemoteRequestEntity entity =
                RemoteRequestEntity.builder()
                        .employeeId(empId)
                        .remoteDate(request.getRemoteDate())
                        .workType(request.getWorkType())
                        .reason(request.getReason())
                        .status(ApprovalStatus.PENDING_MANAGER)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        return mapper.toResponse(repository.save(entity));
    }

    /** UPDATE – chỉ khi PENDING_MANAGER */
    @Transactional
    public RemoteRequestResponse update(
            Long id,
            String employeeId,
            UpdateRemoteRequest request) {

        RemoteRequestEntity entity = getOwnedPending(id, employeeId);

        entity.setWorkType(request.getWorkType());
        entity.setReason(request.getReason());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(repository.save(entity));
    }

    /** CANCEL – chỉ khi PENDING_MANAGER */
    @Transactional
    public void cancel(Long id, String employeeId) {

        RemoteRequestEntity entity = getOwnedPending(id, employeeId);

        entity.setStatus(ApprovalStatus.CANCLE);
        entity.setUpdatedAt(LocalDateTime.now());

        repository.save(entity);
    }

    /** DETAIL */
    public RemoteRequestResponse detail(Long id) {
        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new BusinessException(
                                ErrorCode.NOT_FOUND,
                                "Không tìm thấy đơn remote"
                        ))
        );
    }

    /** LIST – đơn của tôi */
    public List<RemoteRequestResponse> listMy(String employeeId) {
        return repository.findByEmployeeId(Long.valueOf(employeeId))
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /** ===== COMMON CHECK ===== */
    private RemoteRequestEntity getOwnedPending(
            Long id, String employeeId) {

        RemoteRequestEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Không tìm thấy đơn remote"
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
