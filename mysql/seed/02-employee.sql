USE employee_service;

SET FOREIGN_KEY_CHECKS = 0;

-- =============================
-- RESET DATA (DEV)
-- =============================
TRUNCATE TABLE employee_work_history;
TRUNCATE TABLE employee_contract;
TRUNCATE TABLE employee_education;
TRUNCATE TABLE employee_relationship;
TRUNCATE TABLE employee;
TRUNCATE TABLE job_position;
TRUNCATE TABLE department;

SET FOREIGN_KEY_CHECKS = 1;

-- =============================
-- DEPARTMENT
-- =============================
INSERT INTO department (id, name, description) VALUES
                                                   (1, 'IT', 'Phòng Công nghệ thông tin'),
                                                   (2, 'HR', 'Phòng Nhân sự');

-- =============================
-- JOB POSITION
-- =============================
INSERT INTO job_position (id, name, level) VALUES
                                               (1, 'Developer', 1),
                                               (2, 'Manager', 2),
                                               (3, 'HR Officer', 2),
                                               (4, 'System Admin', 3);

-- =============================
-- EMPLOYEE
-- NOTE:
-- account_id PHẢI KHỚP user.id (identity-service)
-- manager_id dùng employee.id
-- =============================

-- ADMIN
INSERT INTO employee (
    id, account_id, full_name, code,
    department_id, position_id,
    start_work_date, employment_status,
    salary_basic, email
) VALUES (
             1,
             '11111111-1111-1111-1111-111111111111',
             'System Admin',
             'EMP001',
             1, 4,
             '2024-01-01',
             'ACTIVE',
             30000000,
             'admin@company.com'
         );

-- HR
INSERT INTO employee (
    id, account_id, full_name, code,
    department_id, position_id,
    start_work_date, employment_status,
    salary_basic, email
) VALUES (
             2,
             '22222222-2222-2222-2222-222222222222',
             'HR Officer',
             'EMP002',
             2, 3,
             '2024-01-01',
             'ACTIVE',
             20000000,
             'hr@company.com'
         );

-- MANAGER
INSERT INTO employee (
    id, account_id, full_name, code,
    department_id, position_id,
    start_work_date, employment_status,
    salary_basic, email
) VALUES (
             3,
             '33333333-3333-3333-3333-333333333333',
             'IT Manager',
             'EMP003',
             1, 2,
             '2024-01-01',
             'ACTIVE',
             25000000,
             'manager@company.com'
         );

-- EMPLOYEE (report to manager)
INSERT INTO employee (
    id, account_id, full_name, code,
    department_id, position_id, manager_id,
    start_work_date, employment_status,
    salary_basic, email
) VALUES (
             4,
             '44444444-4444-4444-4444-444444444444',
             'IT Developer',
             'EMP004',
             1, 1,
             3,
             '2024-01-01',
             'ACTIVE',
             15000000,
             'employee@company.com'
         );

-- =============================
-- SET HEAD OF DEPARTMENT
-- =============================
UPDATE department SET head_employee_id = 3 WHERE id = 1;
UPDATE department SET head_employee_id = 2 WHERE id = 2;

-- =============================
-- EMPLOYEE CONTRACT
-- =============================
INSERT INTO employee_contract (
    contract_id, employee_id,
    contract_number, contract_type,
    start_date, salary_base, status
) VALUES
      (1, 3, 'HD-IT-MANAGER-001', 'FULLTIME', '2024-01-01', 25000000, 'ACTIVE'),
      (2, 4, 'HD-DEV-001',        'FULLTIME', '2024-01-01', 15000000, 'ACTIVE');

-- =============================
-- EMPLOYEE EDUCATION
-- =============================
INSERT INTO employee_education (
    employee_id, degree, major, school
) VALUES
      (3, 'Bachelor', 'Computer Science', 'HUST'),
      (4, 'Bachelor', 'Software Engineering', 'HUST');

-- =============================
-- EMPLOYEE RELATIONSHIP
-- =============================
INSERT INTO employee_relationship (
    employee_id, full_name, relationship_type, phone_number
) VALUES
    (4, 'Nguyen Van B', 'Father', '0900000000');

-- =============================
-- EMPLOYEE WORK HISTORY
-- =============================
INSERT INTO employee_work_history (
    employee_id, from_date, department_name, position_name, description
) VALUES
      (3, '2022-01-01', 'IT', 'Senior Developer', 'Thăng chức lên Manager'),
      (4, '2023-01-01', 'IT', 'Junior Developer', 'Nhân viên mới');
