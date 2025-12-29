USE request_approval_service;

SET FOREIGN_KEY_CHECKS = 0;

-- =============================
-- RESET DATA (DEV)
-- =============================
TRUNCATE TABLE approval_history;
TRUNCATE TABLE attendance_explanation;
TRUNCATE TABLE leave_request;
TRUNCATE TABLE ot_request;
TRUNCATE TABLE remote_request;

SET FOREIGN_KEY_CHECKS = 1;

-- ======================================================
-- QUY ƯỚC KEY (ĐÃ ĐỒNG BỘ TOÀN HỆ)
-- employee_id:
--   3 = MANAGER
--   4 = EMPLOYEE
-- ======================================================

-- ======================================================
-- 1. ATTENDANCE EXPLANATION
-- ======================================================

-- Đi muộn (đã duyệt 2 cấp)
INSERT INTO attendance_explanation
(id, employee_id, work_date, explanation_type, reason, status)
VALUES
    (1, 4, '2025-12-02', 'LATE', 'Tắc đường buổi sáng', 'APPROVED');

INSERT INTO approval_history
(request_type, request_id, approver_id, approver_role, action, comment)
VALUES
    ('EXPLANATION', 1, 3, 'MANAGER', 'APPROVE', 'Lý do hợp lý'),
    ('EXPLANATION', 1, 2, 'HR', 'APPROVE', 'HR xác nhận');

-- Nghỉ không phép (bị từ chối ở manager)
INSERT INTO attendance_explanation
(id, employee_id, work_date, explanation_type, reason, status)
VALUES
    (2, 4, '2025-12-18', 'ABSENT', 'Quên check-in', 'REJECTED');

INSERT INTO approval_history
(request_type, request_id, approver_id, approver_role, action, comment)
VALUES
    ('EXPLANATION', 2, 3, 'MANAGER', 'REJECT', 'Không báo trước');

-- ======================================================
-- 2. LEAVE REQUEST
-- ======================================================

-- Nghỉ phép năm (đã duyệt)
INSERT INTO leave_request
(id, employee_id, leave_type, from_date, to_date, total_days, reason, status)
VALUES
    (1, 4, 'ANNUAL', '2025-12-16', '2025-12-17', 2.00, 'Nghỉ du lịch', 'APPROVED');

INSERT INTO approval_history
(request_type, request_id, approver_id, approver_role, action, comment)
VALUES
    ('LEAVE', 1, 3, 'MANAGER', 'APPROVE', 'OK'),
    ('LEAVE', 1, 2, 'HR', 'APPROVE', 'Đủ phép');

-- Nghỉ không lương (đang chờ HR)
INSERT INTO leave_request
(id, employee_id, leave_type, from_date, to_date, total_days, reason, status)
VALUES
    (2, 4, 'UNPAID', '2026-01-13', '2026-01-13', 1.00, 'Việc cá nhân', 'PENDING_HR');

INSERT INTO approval_history
(request_type, request_id, approver_id, approver_role, action, comment)
VALUES
    ('LEAVE', 2, 3, 'MANAGER', 'APPROVE', 'Đồng ý');

-- ======================================================
-- 3. OT REQUEST
-- ======================================================

-- OT ngày thường (đã duyệt)
INSERT INTO ot_request
(id, employee_id, ot_date, start_time, end_time, total_hours, reason, status)
VALUES
    (1, 4, '2025-12-10', '18:00:00', '21:00:00', 3.00, 'Fix bug gấp', 'APPROVED');

INSERT INTO approval_history
(request_type, request_id, approver_id, approver_role, action, comment)
VALUES
    ('OT', 1, 3, 'MANAGER', 'APPROVE', 'Cần thiết'),
    ('OT', 1, 2, 'HR', 'APPROVE', 'Hợp lệ');

-- OT bị từ chối
INSERT INTO ot_request
(id, employee_id, ot_date, start_time, end_time, total_hours, reason, status)
VALUES
    (2, 4, '2025-12-22', '18:00:00', '20:00:00', 2.00, 'OT không đăng ký trước', 'REJECTED');

INSERT INTO approval_history
(request_type, request_id, approver_id, approver_role, action, comment)
VALUES
    ('OT', 2, 3, 'MANAGER', 'REJECT', 'Không có kế hoạch');

-- ======================================================
-- 4. REMOTE REQUEST
-- ======================================================

-- Remote full day (đã duyệt)
INSERT INTO remote_request
(id, employee_id, remote_date, work_type, reason, status)
VALUES
    (1, 4, '2025-12-27', 'FULL_DAY', 'Làm việc tại nhà', 'APPROVED');

INSERT INTO approval_history
(request_type, request_id, approver_id, approver_role, action, comment)
VALUES
    ('REMOTE', 1, 3, 'MANAGER', 'APPROVE', 'OK'),
    ('REMOTE', 1, 2, 'HR', 'APPROVE', 'Đã ghi nhận');

-- Remote half day (đang chờ manager)
INSERT INTO remote_request
(id, employee_id, remote_date, work_type, reason, status)
VALUES
    (2, 4, '2026-01-03', 'HALF_DAY', 'Buổi sáng làm việc tại nhà', 'PENDING_MANAGER');
