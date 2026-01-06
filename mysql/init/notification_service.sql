/* =====================================================
   Notification Service Database
   In-app notification cho hệ thống HRM
   ===================================================== */

-- =====================================================
-- 1. Tạo database
-- =====================================================
CREATE DATABASE IF NOT EXISTS notification_service
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE notification_service;

-- =====================================================
-- 2. Bảng notification (Thông báo trong hệ thống)
-- =====================================================
CREATE TABLE notification (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              employee_id BIGINT NOT NULL,             -- người nhận (employee-service)
                              title VARCHAR(255) NOT NULL,
                              content TEXT NOT NULL,
                              notification_type ENUM(
        'REQUEST',
        'PAYROLL',
        'SYSTEM'
    ) NOT NULL,
                              reference_type VARCHAR(50),              -- REQUEST / PAYROLL / TIMESHEET ...
                              reference_id BIGINT,                     -- id nghiệp vụ liên quan
                              is_read BOOLEAN NOT NULL DEFAULT FALSE,
                              created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Index phục vụ query mobile
CREATE INDEX idx_notification_employee
    ON notification(employee_id);

CREATE INDEX idx_notification_read
    ON notification(employee_id, is_read);

CREATE INDEX idx_notification_type
    ON notification(notification_type);

-- =====================================================
-- 3. Bảng notification_event_log (Nhật ký gửi thông báo)
-- =====================================================
CREATE TABLE notification_event_log (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        notification_id BIGINT NOT NULL,
                                        event_type VARCHAR(100) NOT NULL,         -- REQUEST_APPROVED, PAYROLL_PAID...
                                        target_employee_id BIGINT NOT NULL,       -- người nhận (employee)
                                        channel ENUM('IN_APP','PUSH') NOT NULL DEFAULT 'IN_APP',
                                        status ENUM('SUCCESS','FAILED') NOT NULL,
                                        error_message VARCHAR(255),
                                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                        CONSTRAINT fk_event_log_notification
                                            FOREIGN KEY (notification_id)
                                                REFERENCES notification(id)
                                                ON DELETE CASCADE
);

-- Index phục vụ trace & audit
CREATE INDEX idx_event_log_employee
    ON notification_event_log(target_employee_id);

CREATE INDEX idx_event_log_event
    ON notification_event_log(event_type);
