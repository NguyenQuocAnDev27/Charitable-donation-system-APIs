USE sgu_charity;

-- Table: Roles (for role management)
CREATE TABLE `Roles` (
	`role_id` INT AUTO_INCREMENT NOT NULL,
	`role_name` VARCHAR(50) NOT NULL,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`role_id`)
);

-- Insert default roles into Roles table
INSERT INTO `Roles` (`role_name`)
VALUES 
('admin'),
('project_manager'),
('normal_user'),
('guest');

-- Table: Users (with a role_id reference to the Roles table)
CREATE TABLE `Users` (
	`user_id` INT AUTO_INCREMENT NOT NULL,
	`full_name` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	`password_hash` VARCHAR(255) NOT NULL,
	`phone_number` VARCHAR(15),
	`role_id` INT,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`user_id`),
	UNIQUE (`email`),
	FOREIGN KEY (`role_id`) REFERENCES `Roles`(`role_id`)
);

-- Table: Donation_Projects (with modified status and foreign key)
CREATE TABLE `Donation_Projects` (
	`project_id` INT AUTO_INCREMENT NOT NULL,
	`project_name` VARCHAR(255) NOT NULL,
	`description` TEXT,
	`goal_amount` DECIMAL(10, 2) NOT NULL,
	`current_amount` DECIMAL(10, 2) DEFAULT 0,
	`start_date` DATE NOT NULL,
	`end_date` DATE NOT NULL,
	`status` VARCHAR(50) CHECK (`status` IN ('stopped', 'completed', 'pending')) ,
	`project_manager_id` INT,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`project_id`),
	FOREIGN KEY (`project_manager_id`) REFERENCES `Users`(`user_id`)
);


-- Table: Tags
CREATE TABLE `Tags` (
	`tag_id` INT AUTO_INCREMENT NOT NULL,
	`tag_name` VARCHAR(255) NOT NULL,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`tag_id`),
	UNIQUE (`tag_name`)
);

-- Table: Transaction_Pay_Methods (for managing payment methods)
CREATE TABLE `Transaction_Pay_Methods` (
	`pay_method_id` INT AUTO_INCREMENT NOT NULL,
	`pay_method_name` VARCHAR(50) NOT NULL,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`pay_method_id`)
);

-- Insert the payment methods into Transaction_Pay_Methods
INSERT INTO `Transaction_Pay_Methods` (`pay_method_name`)
VALUES 
('VNPAY'),
('Zalopay'),
('Momo');

-- Table: Donations (with donor_name instead of donor_id)
CREATE TABLE `Donations` (
	`donation_id` INT AUTO_INCREMENT NOT NULL,
	`transaction_id` VARCHAR(255) NOT NULL,
	`amount` DECIMAL(10, 2) NOT NULL,
	`donation_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`donor_name` VARCHAR(255),
	`project_id` INT,
	`status` VARCHAR(50) CHECK (`status` IN ('failed', 'processing', 'completed')),
	`pay_method_id` INT,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`donation_id`),
	UNIQUE (`transaction_id`),
	FOREIGN KEY (`project_id`) REFERENCES `Donation_Projects`(`project_id`),
	FOREIGN KEY (`pay_method_id`) REFERENCES `Transaction_Pay_Methods`(`pay_method_id`)
);

-- Table: Notifications
CREATE TABLE `Notifications` (
	`notification_id` INT AUTO_INCREMENT NOT NULL,
	`user_id` INT,
	`message` TEXT NOT NULL,
	`is_read` TINYINT(1) DEFAULT 0,
	`sent_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`notification_id`),
	FOREIGN KEY (`user_id`) REFERENCES `Users`(`user_id`)
);

-- Table: Project_Tags (references Donation_Projects and Tags)
CREATE TABLE `Project_Tags` (
	`project_tag_id` INT AUTO_INCREMENT NOT NULL,
	`project_id` INT,
	`tag_id` INT,
	PRIMARY KEY (`project_tag_id`),
	FOREIGN KEY (`project_id`) REFERENCES `Donation_Projects`(`project_id`),
	FOREIGN KEY (`tag_id`) REFERENCES `Tags`(`tag_id`)
);

-- Table: Reports (references Donation_Projects)
CREATE TABLE `Reports` (
	`report_id` INT AUTO_INCREMENT NOT NULL,
	`project_id` INT,
	`total_donations` DECIMAL(10, 2) NOT NULL,
	`impact` TEXT,
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`report_id`),
	FOREIGN KEY (`project_id`) REFERENCES `Donation_Projects`(`project_id`)
);

use sgu_charity;

-- Insert 10 example users into Users table
INSERT INTO `users` (`full_name`, `email`, `password_hash`, `phone_number`, `role_id`, `created_at`, `updated_at`)
VALUES
('John Doe', 'john.doe@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0123456789', 1, NOW(), NOW()),
('Jane Smith', 'jane.smith@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0987654321', 2, NOW(), NOW()),
('Alice Johnson', 'alice.johnson@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0123467890', 3, NOW(), NOW()),
('Bob Brown', 'bob.brown@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0987612345', 4, NOW(), NOW()),
('Charlie Davis', 'charlie.davis@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0987123456', 1, NOW(), NOW()),
('Emily Wilson', 'emily.wilson@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0987123489', 2, NOW(), NOW()),
('Frank Thomas', 'frank.thomas@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0987654398', 3, NOW(), NOW()),
('Grace Lee', 'grace.lee@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0123498765', 4, NOW(), NOW()),
('Henry Miller', 'henry.miller@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0123987654', 1, NOW(), NOW()),
('Isabella Walker', 'isabella.walker@example.com', '$2a$10$m0YWxgSa8TfCQ81zg/kgrOPHL0KJ16PFa5NhGWME3valiZT.ebERm', '0987123897', 2, NOW(), NOW());
use sgu_charity;

CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

CREATE TABLE `Refresh_Tokens` (
    `token_id` INT AUTO_INCREMENT NOT NULL,
    `user_id` INT NOT NULL,
    `token` VARCHAR(255) NOT NULL,
    `expires_at` DATETIME NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `isEnable` BOOLEAN NOT NULL DEFAULT TRUE,  -- New column added
    PRIMARY KEY (`token_id`),
    UNIQUE (`token`),
    FOREIGN KEY (`user_id`) REFERENCES `Users`(`user_id`) ON DELETE CASCADE
);

INSERT INTO Donation_Projects (project_name, description, goal_amount, current_amount, start_date, end_date, status, project_manager_id, created_at, updated_at)
VALUES 
('Quỹ Vì Người Nghèo', 'Giúp đỡ người nghèo khó khăn', 10000.00, 1000.00, '2024-01-02', '2024-11-30', 'pending', 2, NOW(), NOW()),
('Quỹ Vì Trẻ Em', 'Giúp đỡ trẻ em khó khăn', 20000.00, 1000.00, '2024-01-11', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Mùa Đông Ấm', 'Quyên góp áo ấm cho vùng cao', 40000.00, 1000.00, '2024-01-14', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Nhà Chống Lũ', 'Xây nhà chống lũ cho miền Trung', 10000.00, 1000.00, '2024-01-21', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Góp Sức Vì Biển Đảo', 'Ủng hộ người lính và dân đảo', 30000.00, 1000.00, '2024-01-28', '2024-11-30', 'pending', 2, NOW(), NOW()),
('Quỹ Học Bổng Vừ A Dính', 'Học bổng cho trẻ em dân tộc thiểu số', 50000.00, 1000.00, '2024-01-30', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Chung Tay Vì Người Nghèo', 'Giúp đỡ hộ nghèo', 60000.00, 1000.00, '2024-02-01', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Tấm Lòng Vàng', 'Ủng hộ bệnh nhân nghèo', 80000.00, 1000.00, '2024-02-03', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Cùng Em Đến Trường', 'Xây trường cho trẻ em vùng sâu', 100000.00, 1000.00, '2024-02-05', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Mái Ấm Tình Thương', 'Xây nhà tình nghĩa cho người nghèo', 130000.00, 1000.00, '2024-02-08', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Trái Tim Cho Em', 'Giúp trẻ em bị bệnh tim bẩm sinh', 140000.00, 1000.00, '2024-02-10', '2024-11-30', 'stopped', 2, NOW(), NOW()),
('Chung Tay Vì Sức Khỏe', 'Ủng hộ bệnh viện vùng sâu', 150000.00, 1000.00, '2024-02-15', '2024-11-30', 'pending', 2, NOW(), NOW()),
('Vượt Qua Mùa Lũ', 'Quyên góp đồng bào lũ lụt', 170000.00, 1000.00, '2024-02-20', '2024-11-30', 'pending', 2, NOW(), NOW()),
('Vì Một Trái Tim Khỏe', 'Hỗ trợ mổ tim cho trẻ em nghèo', 180000.00, 1000.00, '2024-02-26', '2024-11-30', 'pending', 2, NOW(), NOW()),
('Giọt Nước Sẻ Chia', 'Cung cấp nước sạch vùng khan hiếm', 110000.00, 1000.00, '2024-02-28', '2024-11-30', 'pending', 2, NOW(), NOW()),
('Mái Ấm Hướng Dương', 'Giúp đỡ trẻ em mồ côi', 10000.00, 1000.00, '2024-03-03', '2024-12-25', 'stopped', 2, NOW(), NOW()),
('Vì Nụ Cười Trẻ Thơ', 'Phẫu thuật nụ cười cho trẻ em hở hàm ếch', 20000.00, 1000.00, '2024-03-05', '2024-12-25', 'stopped', 2, NOW(), NOW()),
('Hành Trình Sức Khỏe', 'Chăm sóc sức khỏe cộng đồng', 30000.00, 1000.00, '2024-03-11', '2024-12-25', 'pending', 2, NOW(), NOW()),
('Sức Mạnh Nhân Đạo', 'Hiến máu nhân đạo', 40000.00, 1000.00, '2024-03-12', '2024-12-25', 'completed', 2, NOW(), NOW()),
('Góp Gạch Xây Trường', 'Xây dựng cơ sở giáo dục ở vùng sâu', 50000.00, 1000.00, '2024-03-16', '2024-12-25', 'completed', 2, NOW(), NOW()),
('Quỹ Ước Mơ Xanh', 'Giúp đỡ người khuyết tật', 60000.00, 1000.00, '2024-03-21', '2024-12-25', 'completed', 2, NOW(), NOW()),
('Cùng Lên Rừng', 'Bảo vệ môi trường rừng', 70000.00, 1000.00, '2024-03-25', '2024-12-25', 'pending', 2, NOW(), NOW()),
('Tình Người Mùa Lũ', 'Hỗ trợ đồng bào lũ lụt miền Trung', 80000.00, 1000.00, '2024-03-28', '2024-12-25', 'completed', 2, NOW(), NOW()),
('Ánh Sáng Đời Em', 'Đem ánh sáng cho trẻ em vùng cao', 90000.00, 1000.00, '2024-03-30', '2024-12-25', 'completed', 2, NOW(), NOW()),
('Ngôi Trường Ước Mơ', 'Quyên góp xây trường học', 100000.00, 1000.00, '2024-04-01', '2024-12-25', 'completed', 2, NOW(), NOW()),
('Chung Sức Cộng Đồng', 'Xây cầu dân sinh vùng sâu vùng xa', 110000.00, 1000.00, '2024-04-12', '2024-12-25', 'completed', 2, NOW(), NOW()),
('Bàn Tay Nhân Ái', 'Hỗ trợ người nghèo vượt qua khó khăn', 120000.00, 1000.00, '2024-04-13', '2024-12-25', 'pending', 2, NOW(), NOW()),
('Hướng Về Miền Trung', 'Hỗ trợ đồng bào miền Trung chịu thiên tai', 130000.00, 1000.00, '2024-06-10', '2024-12-25', 'pending', 2, NOW(), NOW()),
('Vì Tương Lai Xanh', 'Bảo vệ môi trường và trồng cây xanh', 140000.00, 1000.00, '2024-06-21', '2024-12-25', 'completed', 2, NOW(), NOW()),
('Chắp Cánh Ước Mơ', 'Giúp đỡ trẻ em nghèo đến trường', 150000.00, 1000.00, '2024-08-01', '2024-12-25', 'completed', 2, NOW(), NOW());
ALTER TABLE donation_projects
ADD COLUMN is_deleted TINYINT DEFAULT 1;
ALTER TABLE users
ADD COLUMN is_deleted TINYINT DEFAULT 1;

CREATE TABLE ProjectDetailText (
    id INT AUTO_INCREMENT NOT NULL,
    project_id INT NOT NULL,
    content TEXT NOT NULL,
    is_delete INT,
    order_no INT NOT NULL, -- Thêm cột thứ tự
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES Donation_Projects(project_id) ON DELETE CASCADE
);

-- Table: ProjectDetailImage
CREATE TABLE ProjectDetailImage (
    id INT AUTO_INCREMENT NOT NULL,
    project_id INT NOT NULL,
    path_image VARCHAR(255) NOT NULL,
    is_delete INT,
    order_no INT NOT NULL, -- Thêm cột thứ tự
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES Donation_Projects(project_id) ON DELETE CASCADE
);

