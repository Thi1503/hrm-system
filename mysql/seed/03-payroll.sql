/* =====================================================
   FAKE PAYROLL DATA - RUN ONCE
   Month: 2025-12
   Database: payroll_service
   ===================================================== */

USE payroll_service;

SET FOREIGN_KEY_CHECKS = 0;

/* =====================================================
   CLEAN OLD DATA (SAFE FOR DEV)
   ===================================================== */
TRUNCATE TABLE payroll_detail;
TRUNCATE TABLE payroll;
TRUNCATE TABLE timesheet_daily;
TRUNCATE TABLE timesheet_month;
TRUNCATE TABLE salary_structure;

SET FOREIGN_KEY_CHECKS = 1;

/* =====================================================
   1. SALARY STRUCTURE (4 EMPLOYEES)
   ===================================================== */
/*
 employee_id mapping:
 1 = ADMIN
 2 = HR
 3 = MANAGER
 4 = EMPLOYEE
*/

INSERT INTO salary_structure (
    employee_id,
    base_salary,
    allowance,
    ot_rate,
    late_penalty_per_min,
    early_penalty_per_min,
    effective_from
) VALUES
-- ADMIN
(1, 30000000, 2000000, 1.5, 0, 0, '2025-01-01'),

-- HR
(2, 20000000, 1000000, 1.5, 3000, 0, '2025-01-01'),

-- MANAGER
(3, 25000000, 1500000, 1.75, 3000, 0, '2025-01-01'),

-- EMPLOYEE
(4, 15000000, 0, 1.5, 5000, 0, '2025-01-01');

/* =====================================================
   2. TIMESHEET MONTH (2025-12)
   ===================================================== */

INSERT INTO timesheet_month (
    employee_id,
    month,
    total_work_days,
    total_work_minutes,
    late_minutes,
    early_minutes,
    ot_hours,
    leave_days,
    status
) VALUES
-- ADMIN (full attendance)
(1, '2025-12', 23.00, 11040, 0, 0, 0.00, 0.00, 'DRAFT'),

-- HR (1 day leave)
(2, '2025-12', 22.00, 10560, 20, 0, 0.00, 1.00, 'DRAFT'),

-- MANAGER (OT nhẹ)
(3, '2025-12', 23.00, 11040, 0, 0, 5.00, 0.00, 'DRAFT'),

-- EMPLOYEE (theo data attendance bạn seed)
(4, '2025-12', 19.00, 9120, 90, 0, 3.00, 2.00, 'DRAFT');

/* =====================================================
   3. PAYROLL (2025-12)
   ===================================================== */

INSERT INTO payroll (
    employee_id,
    month,
    gross_salary,
    total_deduction,
    net_salary,
    status,
    approved_at
) VALUES
-- ADMIN
(1, '2025-12', 32000000, 0, 32000000, 'DRAFT', NOW()),

-- HR
(2, '2025-12', 20500000, 60000, 20440000, 'DRAFT', NOW()),

-- MANAGER
(3, '2025-12', 27500000, 0, 27500000, 'DRAFT', NOW()),

-- EMPLOYEE (tính đúng theo attendance)
(4, '2025-12', 12758151, 450000, 12308151, 'DRAFT', NOW());

/* =====================================================
   4. PAYROLL DETAIL
   ===================================================== */

/* ===== ADMIN ===== */
INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'BASE_SALARY', 30000000, 'Lương cơ bản'
FROM payroll WHERE employee_id = 1 AND month = '2025-12';

INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'ALLOWANCE', 2000000, 'Phụ cấp'
FROM payroll WHERE employee_id = 1 AND month = '2025-12';

/* ===== HR ===== */
INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'BASE_SALARY', 19500000, 'Lương theo công'
FROM payroll WHERE employee_id = 2 AND month = '2025-12';

INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'ALLOWANCE', 1000000, 'Phụ cấp'
FROM payroll WHERE employee_id = 2 AND month = '2025-12';

INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'LATE_PENALTY', -60000, 'Đi muộn 20 phút'
FROM payroll WHERE employee_id = 2 AND month = '2025-12';

/* ===== MANAGER ===== */
INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'BASE_SALARY', 25000000, 'Lương cơ bản'
FROM payroll WHERE employee_id = 3 AND month = '2025-12';

INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'ALLOWANCE', 1500000, 'Phụ cấp quản lý'
FROM payroll WHERE employee_id = 3 AND month = '2025-12';

INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'OT', 1000000, 'OT 5 giờ'
FROM payroll WHERE employee_id = 3 AND month = '2025-12';

/* ===== EMPLOYEE ===== */
INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'BASE_SALARY', 12391306, 'Lương theo 19 ngày công'
FROM payroll WHERE employee_id = 4 AND month = '2025-12';

INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'OT', 366845, 'OT 3 giờ (1.5x)'
FROM payroll WHERE employee_id = 4 AND month = '2025-12';

INSERT INTO payroll_detail (payroll_id, component_type, amount, description)
SELECT id, 'LATE_PENALTY', -450000, 'Đi muộn 90 phút'
FROM payroll WHERE employee_id = 4 AND month = '2025-12';

/* =====================================================
   END FILE
   ===================================================== */
