package com.hrm.requestapprovalservice.service;

import com.hrm.common.enums.ErrorCode;
import com.hrm.common.exception.BusinessException;
import com.hrm.requestapprovalservice.dto.request.RejectApprovalRequest;
import com.hrm.requestapprovalservice.entity.*;
import com.hrm.requestapprovalservice.enums.*;
import com.hrm.requestapprovalservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApprovalRejectService {

    private final AttendanceExplanationRepository explanationRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final OtRequestRepository otRequestRepository;
    private final RemoteRequestRepository remoteRequestRepository;
    private final ApprovalHistoryRepository approvalHistoryRepository;

    @Transactional
    public void reject(
            Long approverId,
            RejectApprovalRequest request) {

        switch (request.getRequestType()) {
            case EXPLANATION -> rejectExplanation(approverId, request);
            case LEAVE -> rejectLeave(approverId, request);
            case OT -> rejectOt(approverId, request);
            case REMOTE -> rejectRemote(approverId, request);
            default -> throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "Loại đơn không hợp lệ"
            );
        }
    }

    /* ================= EXPLANATION ================= */

    private void rejectExplanation(Long approverId,
                                   RejectApprovalRequest req) {

        AttendanceExplanationEntity entity =
                explanationRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        ApproverRole role = resolveRole(entity.getStatus());

        entity.setStatus(ApprovalStatus.REJECTED);
        explanationRepository.save(entity);

        saveHistory(req, approverId, role);
    }

    /* ================= LEAVE ================= */

    private void rejectLeave(Long approverId,
                             RejectApprovalRequest req) {

        LeaveRequestEntity entity =
                leaveRequestRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        ApproverRole role = resolveRole(entity.getStatus());

        entity.setStatus(ApprovalStatus.REJECTED);
        leaveRequestRepository.save(entity);

        saveHistory(req, approverId, role);
    }

    /* ================= OT ================= */

    private void rejectOt(Long approverId,
                          RejectApprovalRequest req) {

        OtRequestEntity entity =
                otRequestRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        ApproverRole role = resolveRole(entity.getStatus());

        entity.setStatus(ApprovalStatus.REJECTED);
        otRequestRepository.save(entity);

        saveHistory(req, approverId, role);
    }

    /* ================= REMOTE ================= */

    private void rejectRemote(Long approverId,
                              RejectApprovalRequest req) {

        RemoteRequestEntity entity =
                remoteRequestRepository.findById(req.getRequestId())
                        .orElseThrow(this::notFound);

        ApproverRole role = resolveRole(entity.getStatus());

        entity.setStatus(ApprovalStatus.REJECTED);
        remoteRequestRepository.save(entity);

        saveHistory(req, approverId, role);
    }

    /* ================= COMMON ================= */

    private ApproverRole resolveRole(ApprovalStatus status) {

        if (status == ApprovalStatus.PENDING_MANAGER) {
            return ApproverRole.MANAGER;
        }

        if (status == ApprovalStatus.PENDING_HR) {
            return ApproverRole.HR;
        }

        throw new BusinessException(
                ErrorCode.INVALID_REQUEST,
                "Đơn không ở trạng thái có thể từ chối"
        );
    }

    private void saveHistory(RejectApprovalRequest req,
                             Long approverId,
                             ApproverRole role) {

        approvalHistoryRepository.save(
                ApprovalHistoryEntity.builder()
                        .requestType(req.getRequestType())
                        .requestId(req.getRequestId())
                        .approverId(approverId)
                        .approverRole(role)
                        .action(ApprovalAction.REJECT)
                        .comment(req.getComment())
                        .build()
        );
    }

    private BusinessException notFound() {
        return new BusinessException(
                ErrorCode.NOT_FOUND,
                "Không tìm thấy đơn"
        );
    }
}
