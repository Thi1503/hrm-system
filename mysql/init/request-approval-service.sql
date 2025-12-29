/* =====================================================
   Request Approval Service Database
   Giải trình – Nghỉ phép – OT – Remote – Duyệt 2 cấp
   ===================================================== */

-- 1. Tạo database
CREATE DATABASE IF NOT EXISTS request_approval_service
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE request_approval_service;

-- =====================================================
-- 2. Bảng attendance_explanation (Giải trình chấm công)
-- =====================================================
CREATE TABLE attendance_explanation (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        employee_id BIGINT NOT NULL,
                                        work_date DATE NOT NULL,
                                        explanation_type ENUM('LATE','EARLY','ABSENT') NOT NULL,
                                        reason TEXT NOT NULL,
                                        attachment_url VARCHAR(255),
                                        status ENUM(
        'PENDING_MANAGER',
        'PENDING_HR',
        'APPROVED',
        'REJECTED',
        'CANCLE'
    ) NOT NULL DEFAULT 'PENDING_MANAGER',
                                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                            ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_explanation_employee_date
    ON attendance_explanation(employee_id, work_date);

-- =====================================================
-- 3. Bảng leave_request (Đơn nghỉ phép)
-- =====================================================
CREATE TABLE leave_request (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               employee_id BIGINT NOT NULL,
                               leave_type ENUM('ANNUAL','UNPAID','SICK','OTHER') NOT NULL,
                               from_date DATE NOT NULL,
                               to_date DATE NOT NULL,
                               total_days DECIMAL(5,2) NOT NULL,
                               reason TEXT,
                               attachment_url VARCHAR(255),
                               status ENUM(
        'PENDING_MANAGER',
        'PENDING_HR',
        'APPROVED',
        'REJECTED',
        'CANCLE'
    ) NOT NULL DEFAULT 'PENDING_MANAGER',
                               created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                   ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_leave_employee_date
    ON leave_request(employee_id, from_date, to_date);

-- =====================================================
-- 4. Bảng ot_request (Đăng ký OT)
-- =====================================================
CREATE TABLE ot_request (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            employee_id BIGINT NOT NULL,
                            ot_date DATE NOT NULL,
                            start_time TIME NOT NULL,
                            end_time TIME NOT NULL,
                            total_hours DECIMAL(5,2) NOT NULL,
                            reason TEXT,
                            status ENUM(
        'PENDING_MANAGER',
        'PENDING_HR',
        'APPROVED',
        'REJECTED',
        'CANCLE'
    ) NOT NULL DEFAULT 'PENDING_MANAGER',
                            created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_ot_employee_date
    ON ot_request(employee_id, ot_date);

-- =====================================================
-- 5. Bảng remote_request (Đăng ký làm việc từ xa)
-- =====================================================
CREATE TABLE remote_request (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                employee_id BIGINT NOT NULL,
                                remote_date DATE NOT NULL,
                                work_type ENUM('FULL_DAY','HALF_DAY') NOT NULL,
                                reason TEXT NOT NULL,
                                status ENUM(
        'PENDING_MANAGER',
        'PENDING_HR',
        'APPROVED',
        'REJECTED',
        'CANCLE'
    ) NOT NULL DEFAULT 'PENDING_MANAGER',
                                created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                    ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_remote_employee_date
    ON remote_request(employee_id, remote_date);

-- =====================================================
-- 6. Bảng approval_history (Lịch sử duyệt – dùng chung)
-- =====================================================
CREATE TABLE approval_history (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  request_type ENUM('EXPLANATION','LEAVE','OT','REMOTE') NOT NULL,
                                  request_id BIGINT NOT NULL,
                                  approver_id BIGINT NOT NULL,
                                  approver_role ENUM('MANAGER','HR') NOT NULL,
                                  action ENUM('APPROVE','REJECT') NOT NULL,
                                  comment TEXT,
                                  approved_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_approval_request
    ON approval_history(request_type, request_id);

CREATE INDEX idx_approval_approver
    ON approval_history(approver_id);
