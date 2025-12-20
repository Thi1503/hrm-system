/* =====================================================
   Identity Service Database
   Quản lý tài khoản & phân quyền (RBAC)
   ===================================================== */

-- =====================================================
-- 1. Tạo database
-- =====================================================
CREATE DATABASE IF NOT EXISTS identity_service
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE identity_service;

-- =====================================================
-- 2. Bảng user (Tài khoản)
-- =====================================================
CREATE TABLE `user` (
                        id VARCHAR(36) NOT NULL,
                        username VARCHAR(191) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        status ENUM('ACTIVE', 'LOCKED') NOT NULL DEFAULT 'ACTIVE',
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                            ON UPDATE CURRENT_TIMESTAMP,

                        PRIMARY KEY (id),
                        UNIQUE KEY uk_user_username (username)
);

CREATE INDEX idx_user_status ON `user`(status);

-- =====================================================
-- 3. Bảng role (Vai trò)
-- =====================================================
CREATE TABLE role (
                      name VARCHAR(191) NOT NULL,
                      description VARCHAR(255),

                      PRIMARY KEY (name)
);

-- =====================================================
-- 4. Bảng permission (Quyền)
-- =====================================================
CREATE TABLE permission (
                            name VARCHAR(191) NOT NULL,
                            description VARCHAR(255),

                            PRIMARY KEY (name)
);

-- =====================================================
-- 5. Bảng user_roles (Gán vai trò cho tài khoản)
-- =====================================================
CREATE TABLE user_roles (
                            user_id VARCHAR(36) NOT NULL,
                            role_name VARCHAR(191) NOT NULL,

                            PRIMARY KEY (user_id, role_name),

                            CONSTRAINT fk_user_roles_user
                                FOREIGN KEY (user_id)
                                    REFERENCES `user`(id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_user_roles_role
                                FOREIGN KEY (role_name)
                                    REFERENCES role(name)
                                    ON DELETE CASCADE
);

CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_name ON user_roles(role_name);

-- =====================================================
-- 6. Bảng role_permissions (Phân quyền cho vai trò)
-- =====================================================
CREATE TABLE role_permissions (
                                  role_name VARCHAR(191) NOT NULL,
                                  permission_name VARCHAR(191) NOT NULL,

                                  PRIMARY KEY (role_name, permission_name),

                                  CONSTRAINT fk_role_permissions_role
                                      FOREIGN KEY (role_name)
                                          REFERENCES role(name)
                                          ON DELETE CASCADE,

                                  CONSTRAINT fk_role_permissions_permission
                                      FOREIGN KEY (permission_name)
                                          REFERENCES permission(name)
                                          ON DELETE CASCADE
);

CREATE INDEX idx_role_permissions_role_name ON role_permissions(role_name);

-- =====================================================
-- 7. Seed dữ liệu mẫu (OPTIONAL)
-- =====================================================

-- Roles
INSERT INTO role (name, description) VALUES
                                         ('ADMIN', 'Quản trị hệ thống'),
                                         ('HR', 'Nhân sự'),
                                         ('MANAGER', 'Quản lý'),
                                         ('EMPLOYEE', 'Nhân viên');

-- Permissions
INSERT INTO permission (name, description) VALUES
                                               ('USER_CREATE', 'Tạo tài khoản'),
                                               ('USER_LOCK', 'Khoá tài khoản'),
                                               ('EMPLOYEE_READ', 'Xem thông tin nhân viên'),
                                               ('ATTENDANCE_APPROVE', 'Duyệt chấm công'),
                                               ('PAYROLL_CALCULATE', 'Tính lương');

-- Role → Permission mapping
INSERT INTO role_permissions (role_name, permission_name) VALUES
                                                              ('ADMIN', 'USER_CREATE'),
                                                              ('ADMIN', 'USER_LOCK'),
                                                              ('ADMIN', 'PAYROLL_CALCULATE'),
                                                              ('HR', 'EMPLOYEE_READ'),
                                                              ('HR', 'PAYROLL_CALCULATE'),
                                                              ('MANAGER', 'ATTENDANCE_APPROVE'),
                                                              ('EMPLOYEE', 'EMPLOYEE_READ');
