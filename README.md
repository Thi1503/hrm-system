Chạy dự án
1. mvn clean package -DskipTests
2. docker compose up -d
3. Get-Content mysql/seed/01-identity.sql | docker exec -i mysql mysql -uroot -proot
   Get-Content mysql/seed/02-employee.sql | docker exec -i mysql mysql -uroot -proot
   Get-Content mysql/seed/03-attendance.sql | docker exec -i mysql mysql -uroot -proot
   Get-Content mysql/seed/04-request-approval.sql | docker exec -i mysql mysql -uroot -proot


📘 HRM System – Backend (DATN)

Hệ thống backend quản lý nhân sự (HRM) theo kiến trúc microservice, dùng Spring Boot + Docker + MySQL.
Repo này phục vụ cho Đồ án tốt nghiệp (DATN).

🧱 Kiến trúc hệ thống
hrm-system
├── identity-service          # Quản lý tài khoản & phân quyền (RBAC)
├── employee-service          # Quản lý nhân sự & cơ cấu tổ chức
├── attendance-service        # Chấm công, GPS, đi muộn / về sớm
├── request-approval-service  # Giải trình, nghỉ phép, OT, remote (duyệt 2 cấp)
├── common-lib                # Thư viện dùng chung
├── mysql
│   ├── init                  # Tạo DB + schema (chạy 1 lần)
│   └── seed                  # Data mẫu (chạy chủ động)
├── docker-compose.yml
└── pom.xml                   # Maven parent

⚙️ Công nghệ sử dụng

Java 21

Spring Boot

Spring Data JPA

Spring Security (JWT + BCrypt)

MySQL 8

Docker & Docker Compose

Maven (multi-module)

🚀 HƯỚNG DẪN CHẠY DỰ ÁN
1️⃣ Yêu cầu môi trường

Docker + Docker Compose

JDK 21

Maven 3.9+

2️⃣ Build toàn bộ project

Tại thư mục gốc hrm-system:

mvn clean package -DskipTests

3️⃣ Chạy hệ thống bằng Docker
docker compose up -d


Kiểm tra container:

docker ps


Phải thấy:

mysql

identity-service

employee-service

attendance-service

request-approval-service

🗄️ KHỞI TẠO DATA MẪU (BẮT BUỘC)

⚠️ Không cần vào MySQL, không insert tay.

Chạy theo đúng thứ tự:

Get-Content mysql/seed/01-identity.sql | docker exec -i mysql mysql -uroot -proot
Get-Content mysql/seed/02-employee.sql | docker exec -i mysql mysql -uroot -proot
Get-Content mysql/seed/03-attendance.sql | docker exec -i mysql mysql -uroot -proot
Get-Content mysql/seed/04-request-approval.sql | docker exec -i mysql mysql -uroot -proot


Sau bước này:

Có user + role

Có nhân viên + manager

Có chấm công cả tháng

Có đơn nghỉ, OT, giải trình, duyệt 2 cấp

🔐 Tài khoản test
Username	Password
admin	admin123
hr	hr123
manager	manager123
employee	employee123
🌐 API Endpoints (mặc định)
Service	URL
Identity	http://localhost:8080

Employee	http://localhost:8081/employee

Attendance	http://localhost:8082

Request Approval	http://localhost:8083/request-approval
🔁 Reset data DEV (an toàn)

👉 KHÔNG dùng docker compose down -v

Chỉ cần chạy lại seed:

docker exec -i mysql mysql -uroot -proot < mysql/seed/01-identity.sql
...

❌ Những điều KHÔNG được làm
docker compose down -v


❌ Lệnh này sẽ xoá toàn bộ dữ liệu MySQL.

📌 Ghi chú quan trọng

mysql/init
→ chỉ dùng để tạo database & schema (chạy 1 lần khi DB mới)

mysql/seed
→ dùng để fake data mẫu, có thể chạy lại nhiều lần

Dữ liệu giữa các service đã được đồng bộ key:

user ↔ employee

employee ↔ attendance

employee ↔ request approval

🧪 Kiểm tra nhanh dữ liệu
-- Bảng công
SELECT work_date, status
FROM attendance_service.attendance_daily_summary
WHERE employee_id = 4
ORDER BY work_date;

-- Đơn nghỉ / OT / giải trình
SELECT * FROM request_approval_service.leave_request;
SELECT * FROM request_approval_service.approval_history;


