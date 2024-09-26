use sgu_charity;
CREATE TABLE Users (
	user_id INT AUTO_INCREMENT NOT NULL,
	full_name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password_hash VARCHAR(255) NOT NULL,
	phone_number VARCHAR(15),
	role VARCHAR(50),
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (user_id),
	CHECK (role IN ('admin', 'project_manager', 'donor'))
);
CREATE TABLE Donation_Projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    description TEXT NULL,
    goal_amount DECIMAL(10, 2) NOT NULL,
    current_amount DECIMAL(10, 2) DEFAULT 0 NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) CHECK (status IN ('stopped', 'completed', 'active')) NULL,
    project_manager_id INT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE Donations (
	donation_id INT AUTO_INCREMENT NOT NULL,
	transaction_id VARCHAR(255) NOT NULL UNIQUE,
	amount DECIMAL(10, 2) NOT NULL,
	donation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	donor_id INT,
	project_id INT,
	status VARCHAR(50),
	payment_method VARCHAR(50),
	receipt_url VARCHAR(255),
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (donation_id),
	CHECK (status IN ('failed', 'processing', 'completed')),
	CHECK (payment_method IN ('VNPAY', 'Zalopay', 'Momo'))
);


CREATE TABLE Notifications (
	notification_id INT AUTO_INCREMENT NOT NULL,
	user_id INT,
	message TEXT NOT NULL,
	is_read BIT DEFAULT 0,
	sent_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (notification_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Tags (
	tag_id INT AUTO_INCREMENT NOT NULL,
	tag_name VARCHAR(255) NOT NULL UNIQUE,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (tag_id)
);

CREATE TABLE Project_Tags (
	project_tag_id INT AUTO_INCREMENT NOT NULL,
	project_id INT,
	tag_id INT,
	PRIMARY KEY (project_tag_id),
	FOREIGN KEY (project_id) REFERENCES Donation_Projects(project_id),
	FOREIGN KEY (tag_id) REFERENCES Tags(tag_id)
);

CREATE TABLE Reports (
	report_id INT AUTO_INCREMENT NOT NULL,
	project_id INT,
	total_donations DECIMAL(10, 2) NOT NULL,
	impact TEXT,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (report_id),
	FOREIGN KEY (project_id) REFERENCES Donation_Projects(project_id)
);



ALTER TABLE Donation_Projects 
    MODIFY current_amount DECIMAL(10, 2) DEFAULT 0 NOT NULL;

ALTER TABLE Donation_Projects 
    MODIFY created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE Donation_Projects 
    MODIFY updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE Donations 
    MODIFY donation_date DATETIME DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE Donations 
    MODIFY created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE Notifications 
    MODIFY is_read BIT DEFAULT 0;

ALTER TABLE Notifications 
    MODIFY sent_at DATETIME DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE Reports 
    MODIFY created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE Tags 
    MODIFY created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE Tags 
    MODIFY updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE Users 
    MODIFY created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE Users 
    MODIFY updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Add foreign key constraints
ALTER TABLE Donation_Projects 
    ADD CONSTRAINT fk_project_manager FOREIGN KEY (project_manager_id) REFERENCES Users(user_id);

ALTER TABLE Donations 
    ADD CONSTRAINT fk_donor FOREIGN KEY (donor_id) REFERENCES Users(user_id);

ALTER TABLE Donations 
    ADD CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES Donation_Projects(project_id);

ALTER TABLE Notifications 
    ADD CONSTRAINT fk_user_notification FOREIGN KEY (user_id) REFERENCES Users(user_id);

ALTER TABLE Project_Tags 
    ADD CONSTRAINT fk_project_tag FOREIGN KEY (project_id) REFERENCES Donation_Projects(project_id);

ALTER TABLE Project_Tags 
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES Tags(tag_id);

ALTER TABLE Reports 
    ADD CONSTRAINT fk_report_project FOREIGN KEY (project_id) REFERENCES Donation_Projects(project_id);

-- Add CHECK constraints (MySQL 8.0+ supports CHECK constraints)
ALTER TABLE Donation_Projects 
    ADD CONSTRAINT chk_status CHECK (status IN ('stopped', 'completed', 'active'));

ALTER TABLE Donations 
    ADD CONSTRAINT chk_payment_method CHECK (payment_method IN ('VNPAY', 'Zalopay', 'Momo'));

ALTER TABLE Donations 
    ADD CONSTRAINT chk_donation_status CHECK (status IN ('failed', 'processing', 'completed'));

ALTER TABLE Users 
    ADD CONSTRAINT chk_user_role CHECK (role IN ('admin', 'project_manager', 'donor'));
