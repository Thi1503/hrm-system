USE identity_service;

SET FOREIGN_KEY_CHECKS = 0;

-- =============================
-- RESET DATA (DEV)
-- =============================
TRUNCATE TABLE role_permissions;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE permission;
TRUNCATE TABLE role;
TRUNCATE TABLE `user`;

SET FOREIGN_KEY_CHECKS = 1;

-- =============================
-- ROLES
-- =============================
INSERT INTO role (name, description) VALUES
                                         ('ADMIN', 'Quản trị hệ thống'),
                                         ('HR', 'Nhân sự'),
                                         ('MANAGER', 'Quản lý'),
                                         ('EMPLOYEE', 'Nhân viên');

-- =============================
-- PERMISSIONS
-- =============================
INSERT INTO permission (name, description) VALUES
                                               ('USER_CREATE', 'Tạo tài khoản'),
                                               ('USER_LOCK', 'Khoá tài khoản'),
                                               ('EMPLOYEE_READ', 'Xem thông tin nhân viên'),
                                               ('ATTENDANCE_APPROVE', 'Duyệt chấm công'),
                                               ('PAYROLL_CALCULATE', 'Tính lương');

-- =============================
-- ROLE → PERMISSION
-- =============================
INSERT INTO role_permissions (role_name, permission_name) VALUES
                                                              ('ADMIN', 'USER_CREATE'),
                                                              ('ADMIN', 'USER_LOCK'),
                                                              ('ADMIN', 'PAYROLL_CALCULATE'),
                                                              ('HR', 'EMPLOYEE_READ'),
                                                              ('HR', 'PAYROLL_CALCULATE'),
                                                              ('MANAGER', 'ATTENDANCE_APPROVE'),
                                                              ('EMPLOYEE', 'EMPLOYEE_READ');

-- =============================
-- USERS (BCrypt by Spring)
-- =============================
INSERT INTO `user` (id, username, password, status) VALUES
                                                        ('11111111-1111-1111-1111-111111111111', 'admin',
                                                         '$2a$10$fCG4UPWnW3hxo9bWVVZB.u1fIzpu419inqRp6wP6A0FD/LbJ9FqW', 'ACTIVE'),

                                                        ('22222222-2222-2222-2222-222222222222', 'hr',
                                                         '$2a$10$w5cafTs5eMMB5CUAU1E.9.b1H6fbet5vAD2Q5bi5g9STQl0L0PKb.', 'ACTIVE'),

                                                        ('33333333-3333-3333-3333-333333333333', 'manager',
                                                         '$2a$10$sObysOwUBt.wf16L6R4yaeu004qPzseiprkJ/cAAzc/7TFk6R81aS', 'ACTIVE'),

                                                        ('44444444-4444-4444-4444-444444444444', 'employee',
                                                         '$2a$10$n8D.tVA6Hs9xhZRV7U.vLesJBMwpud61MYJ6toFkK3uZmHy89fm', 'ACTIVE');

-- =============================
-- USER → ROLE
-- =============================
INSERT INTO user_roles (user_id, role_name) VALUES
                                                ('11111111-1111-1111-1111-111111111111', 'ADMIN'),
                                                ('22222222-2222-2222-2222-222222222222', 'HR'),
                                                ('33333333-3333-3333-3333-333333333333', 'MANAGER'),
                                                ('44444444-4444-4444-4444-444444444444', 'EMPLOYEE');
