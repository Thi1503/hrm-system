/* =====================================================
   Employee Service Database
   Quản lý nhân sự & cơ cấu tổ chức
   ===================================================== */

-- 1. Tạo database
CREATE DATABASE IF NOT EXISTS employee_service
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE employee_service;

-- =====================================================
-- 2. Bảng department (Phòng ban)
-- =====================================================
CREATE TABLE department (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            head_employee_id BIGINT,
                            description VARCHAR(255),
                            created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- 3. Bảng position (Chức vụ)
-- =====================================================
CREATE TABLE job_position (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              level INT,
                              created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- 4. Bảng employee (Nhân viên)
-- =====================================================
CREATE TABLE employee (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          account_id VARCHAR(36) NOT NULL,
                          full_name VARCHAR(255) NOT NULL,
                          code VARCHAR(50) NOT NULL UNIQUE,
                          gender ENUM('MALE','FEMALE','OTHER'),
                          date_of_birth DATE,
                          citizen_id VARCHAR(20),
                          issued_date DATE,
                          issued_place VARCHAR(255),
                          phone_number VARCHAR(20),
                          email VARCHAR(255),
                          address VARCHAR(255),
                          department_id BIGINT NOT NULL,
                          position_id BIGINT NOT NULL,
                          company_id BIGINT,
                          manager_id BIGINT,
                          start_work_date DATE NOT NULL,
                          end_work_date DATE,
                          employment_status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
                          salary_basic DECIMAL(15,2),
                          extra_info TEXT,
                          avatar_url VARCHAR(255),
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                          CONSTRAINT fk_employee_department
                              FOREIGN KEY (department_id)
                                  REFERENCES department(id),

                          CONSTRAINT fk_employee_position
                              FOREIGN KEY (position_id)
                                  REFERENCES job_position(id),

                          CONSTRAINT fk_employee_manager
                              FOREIGN KEY (manager_id)
                                  REFERENCES employee(id)
);

CREATE INDEX idx_employee_account ON employee(account_id);
CREATE INDEX idx_employee_department ON employee(department_id);
CREATE INDEX idx_employee_position ON employee(position_id);
CREATE INDEX idx_employee_status ON employee(employment_status);

-- =====================================================
-- 5. Bảng employee_relationship (Quan hệ nhân viên)
-- =====================================================
CREATE TABLE employee_relationship (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       employee_id BIGINT NOT NULL,
                                       full_name VARCHAR(255) NOT NULL,
                                       relationship_type VARCHAR(50) NOT NULL,
                                       phone_number VARCHAR(20),
                                       citizen_id VARCHAR(20),
                                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                       CONSTRAINT fk_relationship_employee
                                           FOREIGN KEY (employee_id)
                                               REFERENCES employee(id)
                                               ON DELETE CASCADE
);

-- =====================================================
-- 6. Bảng employee_education (Bằng cấp)
-- =====================================================
CREATE TABLE employee_education (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    employee_id BIGINT NOT NULL,
                                    degree VARCHAR(255) NOT NULL,
                                    major VARCHAR(255),
                                    school VARCHAR(255),
                                    start_date DATE,
                                    end_date DATE,
                                    description TEXT,
                                    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                    CONSTRAINT fk_education_employee
                                        FOREIGN KEY (employee_id)
                                            REFERENCES employee(id)
                                            ON DELETE CASCADE
);

-- =====================================================
-- 7. Bảng employee_contract (Hợp đồng lao động)
-- =====================================================
CREATE TABLE employee_contract (
                                   contract_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   employee_id BIGINT NOT NULL,
                                   contract_number VARCHAR(100) NOT NULL,
                                   contract_type VARCHAR(50) NOT NULL,
                                   start_date DATE NOT NULL,
                                   end_date DATE,
                                   salary_index DECIMAL(10,2),
                                   salary_base DECIMAL(15,2) NOT NULL,
                                   position VARCHAR(255),
                                   contract_url VARCHAR(255),
                                   status ENUM('ACTIVE','EXPIRED') NOT NULL DEFAULT 'ACTIVE',
                                   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                   CONSTRAINT fk_contract_employee
                                       FOREIGN KEY (employee_id)
                                           REFERENCES employee(id)
                                           ON DELETE CASCADE
);

CREATE UNIQUE INDEX idx_contract_number ON employee_contract(contract_number);

-- =====================================================
-- 8. Bảng employee_work_history (Quá trình công tác)
-- =====================================================
CREATE TABLE employee_work_history (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       employee_id BIGINT NOT NULL,
                                       from_date DATE NOT NULL,
                                       to_date DATE,
                                       department_name VARCHAR(255),
                                       position_name VARCHAR(255),
                                       description TEXT,
                                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                       CONSTRAINT fk_work_history_employee
                                           FOREIGN KEY (employee_id)
                                               REFERENCES employee(id)
                                               ON DELETE CASCADE
);

-- =====================================================
-- (OPTIONAL) 9. Index tối ưu truy vấn
-- =====================================================
CREATE INDEX idx_work_history_employee ON employee_work_history(employee_id);
CREATE INDEX idx_education_employee ON employee_education(employee_id);
CREATE INDEX idx_relationship_employee ON employee_relationship(employee_id);
