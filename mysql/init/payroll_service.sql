/* =====================================================
   Payroll Service Database
   Tính công – tính lương – chốt lương
   ===================================================== */

-- 1. Tạo database
CREATE DATABASE IF NOT EXISTS payroll_service
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE payroll_service;

-- =====================================================
-- 2. Bảng timesheet_month (Bảng công theo tháng)
-- =====================================================
CREATE TABLE timesheet_month (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 employee_id BIGINT NOT NULL,
                                 month VARCHAR(7) NOT NULL, -- YYYY-MM
                                 total_work_days DECIMAL(5,2) NOT NULL DEFAULT 0,
                                 total_work_minutes INT NOT NULL DEFAULT 0,
                                 late_minutes INT NOT NULL DEFAULT 0,
                                 early_minutes INT NOT NULL DEFAULT 0,
                                 ot_hours DECIMAL(5,2) NOT NULL DEFAULT 0,
                                 leave_days DECIMAL(5,2) NOT NULL DEFAULT 0,
                                 status ENUM('DRAFT','CLOSED','PAID') NOT NULL DEFAULT 'DRAFT',
                                 created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                     ON UPDATE CURRENT_TIMESTAMP,

                                 CONSTRAINT uk_timesheet_employee_month
                                     UNIQUE (employee_id, month)
);

CREATE INDEX idx_timesheet_month_employee
    ON timesheet_month(employee_id);

-- =====================================================
-- 3. Bảng timesheet_daily (Công theo ngày)
-- =====================================================
CREATE TABLE timesheet_daily (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 timesheet_month_id BIGINT NOT NULL,
                                 work_date DATE NOT NULL,
                                 work_type ENUM(
        'NORMAL',
        'LEAVE',
        'OT',
        'REMOTE',
        'ABSENT'
    ) NOT NULL DEFAULT 'NORMAL',
                                 work_minutes INT NOT NULL DEFAULT 0,
                                 late_minutes INT NOT NULL DEFAULT 0,
                                 early_minutes INT NOT NULL DEFAULT 0,
                                 ot_minutes INT NOT NULL DEFAULT 0,
                                 note TEXT,
                                 created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                 CONSTRAINT fk_timesheet_daily_month
                                     FOREIGN KEY (timesheet_month_id)
                                         REFERENCES timesheet_month(id)
                                         ON DELETE CASCADE,

                                 CONSTRAINT uk_timesheet_day
                                     UNIQUE (timesheet_month_id, work_date)
);

-- =====================================================
-- 4. Bảng salary_structure (Cấu trúc lương)
-- =====================================================
CREATE TABLE salary_structure (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  employee_id BIGINT NOT NULL,
                                  base_salary DECIMAL(15,2) NOT NULL,
                                  allowance DECIMAL(15,2) NOT NULL DEFAULT 0,
                                  ot_rate DECIMAL(5,2) NOT NULL DEFAULT 1.5,
                                  late_penalty_per_min DECIMAL(10,2) NOT NULL DEFAULT 0,
                                  early_penalty_per_min DECIMAL(10,2) NOT NULL DEFAULT 0,
                                  effective_from DATE NOT NULL,
                                  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_salary_employee
    ON salary_structure(employee_id);

-- =====================================================
-- 5. Bảng payroll (Bảng lương tháng)
-- =====================================================
CREATE TABLE payroll (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         employee_id BIGINT NOT NULL,
                         month VARCHAR(7) NOT NULL, -- YYYY-MM
                         gross_salary DECIMAL(15,2) NOT NULL,
                         total_deduction DECIMAL(15,2) NOT NULL DEFAULT 0,
                         net_salary DECIMAL(15,2) NOT NULL,
                         status ENUM('DRAFT','APPROVED','PAID') NOT NULL DEFAULT 'DRAFT',
                         created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         approved_at DATETIME,

                         CONSTRAINT uk_payroll_employee_month
                             UNIQUE (employee_id, month)
);

CREATE INDEX idx_payroll_employee
    ON payroll(employee_id);

-- =====================================================
-- 6. Bảng payroll_detail (Chi tiết lương)
-- =====================================================
CREATE TABLE payroll_detail (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                payroll_id BIGINT NOT NULL,
                                component_type ENUM(
        'BASE_SALARY',
        'ALLOWANCE',
        'OT',
        'LATE_PENALTY',
        'EARLY_PENALTY',
        'OTHER'
    ) NOT NULL,
                                amount DECIMAL(15,2) NOT NULL,
                                description VARCHAR(255),
                                created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT fk_payroll_detail_payroll
                                    FOREIGN KEY (payroll_id)
                                        REFERENCES payroll(id)
                                        ON DELETE CASCADE
);

CREATE INDEX idx_payroll_detail_payroll
    ON payroll_detail(payroll_id);
