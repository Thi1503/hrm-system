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
                                                         '$2a$10$4bvzRbcTvTyUDHLcxyA8Ku7ksKwFpNAt9aooyidSDLgtQk5vnjmiO', 'ACTIVE'),

                                                        ('22222222-2222-2222-2222-222222222222', 'hr',
                                                         '$2a$10$CsB7S7Qy7W9z9nujsziaQ.nyHG14pkgI0pe9Gb/Cp9GsqzMqGEYQu', 'ACTIVE'),

                                                        ('33333333-3333-3333-3333-333333333333', 'manager',
                                                         '$2a$10$OC1qjIsaQpBIq7AunYSFb.4b3KN/JyPXQpe05vroa.ZS6Qwjpj53i', 'ACTIVE'),

                                                        ('44444444-4444-4444-4444-444444444444', 'employee',
                                                         '$2a$10$vLvyJQqNe6O2de.eGBGa.OTT43/KJi1IMG4PPtFDFWlduzqU34SbS', 'ACTIVE');

-- =============================
-- USER → ROLE
-- =============================
INSERT INTO user_roles (user_id, role_name) VALUES
                                                ('11111111-1111-1111-1111-111111111111', 'ADMIN'),
                                                ('22222222-2222-2222-2222-222222222222', 'HR'),
                                                ('33333333-3333-3333-3333-333333333333', 'MANAGER'),
                                                ('44444444-4444-4444-4444-444444444444', 'EMPLOYEE');
