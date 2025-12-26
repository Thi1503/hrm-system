/* =====================================================
   Attendance Service Database
   Chấm công – GPS – đi muộn / về sớm
   ===================================================== */

-- 1. Tạo database
CREATE DATABASE IF NOT EXISTS attendance_service
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE attendance_service;

-- =====================================================
-- 2. Bảng attendance_location_rule (Cấu hình GPS)
-- =====================================================
CREATE TABLE attendance_location_rule (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
                                          latitude DECIMAL(10,6) NOT NULL,
                                          longitude DECIMAL(10,6) NOT NULL,
                                          radius_meter INT NOT NULL DEFAULT 100,
                                          is_active BOOLEAN NOT NULL DEFAULT TRUE,
                                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_location_active ON attendance_location_rule(is_active);

-- =====================================================
-- 3. Bảng attendance_shift (Ca làm việc)
-- =====================================================
CREATE TABLE attendance_shift (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  name VARCHAR(100) NOT NULL,
                                  start_time TIME NOT NULL,
                                  end_time TIME NOT NULL,
                                  late_threshold_min INT NOT NULL DEFAULT 0,
                                  early_threshold_min INT NOT NULL DEFAULT 0,
                                  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- 4. Bảng attendance_log (Nhật ký check-in / check-out)
-- =====================================================
CREATE TABLE attendance_log (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                employee_id BIGINT NOT NULL,
                                check_time DATETIME NOT NULL,
                                check_type ENUM('IN','OUT') NOT NULL,
                                latitude DECIMAL(10,6) NOT NULL,
                                longitude DECIMAL(10,6) NOT NULL,
                                location_rule_id BIGINT,
                                is_valid_location BOOLEAN NOT NULL DEFAULT TRUE,
                                device_info VARCHAR(255),
                                created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT fk_attendance_log_location
                                    FOREIGN KEY (location_rule_id)
                                        REFERENCES attendance_location_rule(id)
);

CREATE INDEX idx_attendance_log_employee_time
    ON attendance_log(employee_id, check_time);

CREATE INDEX idx_attendance_log_type
    ON attendance_log(check_type);

-- =====================================================
-- 5. Bảng attendance_daily_summary (Tổng hợp theo ngày)
-- =====================================================
CREATE TABLE attendance_daily_summary (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          employee_id BIGINT NOT NULL,
                                          work_date DATE NOT NULL,
                                          shift_id BIGINT NOT NULL,
                                          check_in_time DATETIME,
                                          check_out_time DATETIME,
                                          late_minutes INT NOT NULL DEFAULT 0,
                                          early_minutes INT NOT NULL DEFAULT 0,
                                          work_minutes INT NOT NULL DEFAULT 0,
                                          status ENUM('NORMAL','LATE','EARLY','ABSENT') NOT NULL DEFAULT 'NORMAL',
                                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                          CONSTRAINT fk_daily_summary_shift
                                              FOREIGN KEY (shift_id)
                                                  REFERENCES attendance_shift(id),

                                          CONSTRAINT uk_employee_work_date
                                              UNIQUE (employee_id, work_date)
);

CREATE INDEX idx_daily_summary_employee
    ON attendance_daily_summary(employee_id);

CREATE INDEX idx_daily_summary_date
    ON attendance_daily_summary(work_date);
