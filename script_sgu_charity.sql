﻿
﻿USE [sgu_charity]
GO
/****** Object:  Table [dbo].[Donation_Projects]    Script Date: 19/09/2024 13:57:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Donation_Projects](
	[project_id] [int] IDENTITY(1,1) NOT NULL,
	[project_name] [varchar](255) NOT NULL,
	[description] [text] NULL,
	[goal_amount] [decimal](10, 2) NOT NULL,
	[current_amount] [decimal](10, 2) NULL,
	[start_date] [date] NOT NULL,
	[end_date] [date] NOT NULL,
	[status] [varchar](50) NULL,
	[project_manager_id] [int] NULL,
	[created_at] [datetime] NULL,
	[updated_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[project_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Donations]    Script Date: 19/09/2024 13:57:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Donations](
	[donation_id] [int] IDENTITY(1,1) NOT NULL,
	[transaction_id] [varchar](255) NOT NULL,
	[amount] [decimal](10, 2) NOT NULL,
	[donation_date] [datetime] NULL,
	[donor_id] [int] NULL,
	[project_id] [int] NULL,
	[status] [varchar](50) NULL,
	[payment_method] [varchar](50) NULL,
	[receipt_url] [varchar](255) NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[donation_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[transaction_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Notifications]    Script Date: 19/09/2024 13:57:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Notifications](
	[notification_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NULL,
	[message] [text] NOT NULL,
	[is_read] [bit] NULL,
	[sent_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[notification_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Project_Tags]    Script Date: 19/09/2024 13:57:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Project_Tags](
	[project_tag_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NULL,
	[tag_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[project_tag_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reports]    Script Date: 19/09/2024 13:57:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reports](
	[report_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NULL,
	[total_donations] [decimal](10, 2) NOT NULL,
	[impact] [text] NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[report_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Tags]    Script Date: 19/09/2024 13:57:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Tags](
	[tag_id] [int] IDENTITY(1,1) NOT NULL,
	[tag_name] [varchar](255) NOT NULL,
	[created_at] [datetime] NULL,
	[updated_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[tag_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[tag_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 19/09/2024 13:57:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[full_name] [varchar](255) NOT NULL,
	[email] [varchar](255) NOT NULL,
	[password_hash] [varchar](255) NOT NULL,
	[phone_number] [varchar](15) NULL,
	[role] [varchar](50) NULL,
	[created_at] [datetime] NULL,
	[updated_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Donation_Projects] ADD  DEFAULT ((0)) FOR [current_amount]
GO
ALTER TABLE [dbo].[Donation_Projects] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Donation_Projects] ADD  DEFAULT (getdate()) FOR [updated_at]
GO
ALTER TABLE [dbo].[Donations] ADD  DEFAULT (getdate()) FOR [donation_date]
GO
ALTER TABLE [dbo].[Donations] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Notifications] ADD  DEFAULT ((0)) FOR [is_read]
GO
ALTER TABLE [dbo].[Notifications] ADD  DEFAULT (getdate()) FOR [sent_at]
GO
ALTER TABLE [dbo].[Reports] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Tags] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Tags] ADD  DEFAULT (getdate()) FOR [updated_at]
GO
ALTER TABLE [dbo].[Users] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Users] ADD  DEFAULT (getdate()) FOR [updated_at]
GO
ALTER TABLE [dbo].[Donation_Projects]  WITH CHECK ADD FOREIGN KEY([project_manager_id])
REFERENCES [dbo].[Users] ([user_id])
GO
ALTER TABLE [dbo].[Donations]  WITH CHECK ADD FOREIGN KEY([donor_id])
REFERENCES [dbo].[Users] ([user_id])
GO
ALTER TABLE [dbo].[Donations]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [dbo].[Donation_Projects] ([project_id])
GO
ALTER TABLE [dbo].[Notifications]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[Users] ([user_id])
GO
ALTER TABLE [dbo].[Project_Tags]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [dbo].[Donation_Projects] ([project_id])
GO
ALTER TABLE [dbo].[Project_Tags]  WITH CHECK ADD FOREIGN KEY([tag_id])
REFERENCES [dbo].[Tags] ([tag_id])
GO
ALTER TABLE [dbo].[Reports]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [dbo].[Donation_Projects] ([project_id])
GO
ALTER TABLE [dbo].[Donation_Projects]  WITH CHECK ADD CHECK  (([status]='stopped' OR [status]='completed' OR [status]='active'))
GO
ALTER TABLE [dbo].[Donations]  WITH CHECK ADD CHECK  (([payment_method]='VNPAY' OR [payment_method]='Zalopay' OR [payment_method]='Momo'))
GO
ALTER TABLE [dbo].[Donations]  WITH CHECK ADD CHECK  (([status]='failed' OR [status]='processing' OR [status]='completed'))
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD CHECK  (([role]='admin' OR [role]='project_manager' OR [role]='donor'))
GO

INSERT INTO [dbo].[Users] ([full_name], [email], [password_hash], [phone_number], [role], [created_at], [updated_at])
VALUES 
('John Doe', 'johndoe@example.com', 'hashed_password_here', '1234567890', 'admin', GETDATE(), GETDATE());

INSERT INTO [dbo].[Users] ([full_name], [email], [password_hash], [phone_number], [role], [created_at], [updated_at])
VALUES 
('User 1', 'user1@example.com', '1234567890', '1234567890', 'donor', GETDATE(), GETDATE()),
('User 2', 'user2@example.com', '1234567890', '1234567890', 'donor', GETDATE(), GETDATE()),
('User 3', 'user3@example.com', '1234567890', '1234567890', 'donor', GETDATE(), GETDATE());
INSERT INTO [dbo].[Donation_Projects] ([project_name] ,[description] ,[goal_amount] ,[current_amount] ,[start_date] ,[end_date] ,[status] ,[project_manager_id] , [created_at], [updated_at])
VALUES 
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 10000.00, 1000.00, '2024-01-02', '2024-11-30', 'active', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 20000.00, 1000.00, '2024-01-11', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 40000.00, 1000.00, '2024-01-14', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 10000.00, 1000.00, '2024-01-21', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 30000.00, 1000.00, '2024-01-28', '2024-11-30', 'active', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 50000.00, 1000.00, '2024-01-30', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 60000.00, 1000.00, '2024-02-01', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 80000.00, 1000.00, '2024-02-03', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 100000.00, 1000.00, '2024-02-05', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn',130000.00, 1000.00, '2024-02-08', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 140000.00, 1000.00, '2024-02-10', '2024-11-30', 'stopped', 1, GETDATE(), GETDATE()),
('Góp Heo Vàng', 'Giúp đỡ trẻ em khó khăn', 150000.00, 1000.00, '2024-02-15', '2024-11-30', 'active', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 170000.00, 1000.00, '2024-02-20', '2024-11-30', 'active', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 180000.00, 1000.00, '2024-02-26', '2024-11-30', 'active', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 110000.00, 1000.00, '2024-02-28', '2024-11-30', 'active', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 10000.00, 1000.00, '2024-03-03', '2024-12-25', 'stopped', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 20000.00, 1000.00, '2024-03-05', '2024-12-25', 'stopped', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 30000.00, 1000.00, '2024-03-11', '2024-12-25', 'active', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 40000.00, 1000.00, '2024-03-12', '2024-12-25', 'completed', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 50000.00, 1000.00, '2024-03-16', '2024-12-25', 'completed', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 60000.00, 1000.00, '2024-03-21', '2024-12-25', 'completed', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 70000.00, 1000.00, '2024-03-25', '2024-12-25', 'active', 1, GETDATE(), GETDATE()),
('Vượt qua mủa lũ', 'quyên góp đồng bào lũ lụt', 80000.00, 1000.00, '2024-03-28', '2024-12-25', 'completed', 1, GETDATE(), GETDATE()),
('Góp gạch xay trường', 'quyên góp giúp đỡ xây dựng cơ sở giáo dục ở vùng sâu vùng xa', 90000.00, 1000.00, '2024-03-30', '2024-12-25', 'completed', 1, GETDATE(), GETDATE()),
('Góp gạch xay trường', 'quyên góp giúp đỡ xây dựng cơ sở giáo dục ở vùng sâu vùng xa', 100000.00, 1000.00, '2024-04-01', '2024-12-25', 'completed', 1, GETDATE(), GETDATE()),
('Góp gạch xay trường', 'quyên góp giúp đỡ xây dựng cơ sở giáo dục ở vùng sâu vùng xa', 110000.00, 1000.00, '2024-04-12', '2024-12-25', 'completed', 1, GETDATE(), GETDATE()),
('Góp gạch xay trường', 'quyên góp giúp đỡ xây dựng cơ sở giáo dục ở vùng sâu vùng xa', 120000.00, 1000.00, '2024-04-13', '2024-12-25', 'active', 1, GETDATE(), GETDATE()),
('Góp gạch xay trường', 'quyên góp giúp đỡ xây dựng cơ sở giáo dục ở vùng sâu vùng xa', 130000.00, 1000.00, '2024-06-10', '2024-12-25', 'active', 1, GETDATE(), GETDATE()),
('Góp gạch xay trường', 'quyên góp giúp đỡ xây dựng cơ sở giáo dục ở vùng sâu vùng xa', 140000.00, 1000.00, '2024-06-21', '2024-12-25', 'completed', 1, GETDATE(), GETDATE()),
('Góp gạch xay trường', 'quyên góp giúp đỡ xây dựng cơ sở giáo dục ở vùng sâu vùng xa', 150000.00, 1000.00, '2024-08-01', '2024-12-25', 'completed', 1, GETDATE(), GETDATE());


INSERT INTO dbo.Donations (transaction_id, amount, donation_date, donor_id, project_id, status, payment_method, receipt_url, created_at)
VALUES 
('GHV123', 100.00, GETDATE(), 4, 6, 'completed', 'VNPAY', 'url_receipt_123', GETDATE()),
('GHV124', 100.00, GETDATE(), 5, 7, 'failed', 'Zalopay', 'url_receipt_124', GETDATE()),
('GHV125', 100.00, GETDATE(), 6, 8, 'failed', 'Zalopay', 'url_receipt_125', GETDATE()),
('GHV126', 100.00, GETDATE(), 6, 9, 'failed', 'Zalopay', 'url_receipt_126', GETDATE()),
('GHV127', 100.00, GETDATE(), 5, 10, 'failed', 'Momo', 'url_receipt_127', GETDATE()),
('GHV128', 100.00, GETDATE(), 4, 11, 'failed', 'Momo', 'url_receipt_128', GETDATE()),
('GHV129', 100.00, GETDATE(), 5, 12, 'failed', 'VNPAY', 'url_receipt_129', GETDATE()),
('GHV120', 100.00, GETDATE(), 6, 13, 'failed', 'Zalopay', 'url_receipt_130', GETDATE()),
('GHV121', 100.00, GETDATE(), 6, 14, 'failed', 'VNPAY', 'url_receipt_131', GETDATE()),
('GHV131', 100.00, GETDATE(), 5, 15, 'failed', 'VNPAY', 'url_receipt_111', GETDATE()),
('GHV132', 100.00, GETDATE(), 4, 16, 'completed', 'Zalopay', 'url_receipt_112', GETDATE()),
('GHV133', 100.00, GETDATE(), 4, 17, 'failed', 'VNPAY', 'url_receipt_114', GETDATE()),
('GHV134', 100.00, GETDATE(), 6, 6, 'completed', 'VNPAY', 'url_receipt_1115', GETDATE()),
('GHV135', 100.00, GETDATE(), 5, 7, 'completed', 'VNPAY', 'url_receipt_116', GETDATE()),
('GHV136', 100.00, GETDATE(), 5, 18, 'processing', 'Zalopay', 'url_receipt_117', GETDATE()),
('VQL137', 100.00, GETDATE(), 6, 19, 'completed', 'VNPAY', 'url_receipt_118', GETDATE()),
('VQL140', 100.00, GETDATE(), 4, 20, 'processing', 'VNPAY', 'url_receipt_119', GETDATE()),
('VQL144', 100.00, GETDATE(), 5, 21, 'processing', 'VNPAY', 'url_receipt_198', GETDATE()),
('VQL234', 100.00, GETDATE(), 4, 22, 'completed', 'VNPAY', 'url_receipt_199', GETDATE()),
('VQL456', 100.00, GETDATE(), 6, 23, 'completed', 'VNPAY', 'url_receipt_197', GETDATE()),
('VQL345', 100.00, GETDATE(), 4, 24, 'completed', 'VNPAY', 'url_receipt_196', GETDATE()),
('VQL344', 100.00, GETDATE(), 4, 26, 'processing', 'VNPAY', 'url_receipt_195', GETDATE()),
('VQL367', 100.00, GETDATE(), 4, 27, 'completed', 'VNPAY', 'url_receipt_194', GETDATE()),
('VQL177', 100.00, GETDATE(), 5, 28, 'processing', 'VNPAY', 'url_receipt_193', GETDATE()),
('GG776', 100.00, GETDATE(), 5, 29, 'completed', 'Momo', 'url_receipt_192', GETDATE()),
('GG677', 100.00, GETDATE(),5, 30, 'failed', 'Momo', 'url_receipt_191', GETDATE()),
('GG113', 100.00, GETDATE(),6, 31, 'failed', 'Momo', 'url_receipt_189', GETDATE()),
('GG232', 100.00, GETDATE(), 6,32, 'processing', 'VNPAY', 'url_receipt_188', GETDATE()),
('GG155', 100.00, GETDATE(), 6, 33, 'processing', 'VNPAY', 'url_receipt_99', GETDATE()),
('GG156', 100.00, GETDATE(), 4, 34, 'processing', 'Zalopay', 'url_receipt_77', GETDATE()),
('GG179', 100.00, GETDATE(), 5, 35, 'processing', 'Zalopay', 'url_receipt_34', GETDATE()),
('GG342', 100.00, GETDATE(), 6, 30, 'processing', 'VNPAY', 'url_receipt_54', GETDATE()),
('GG111', 100.00, GETDATE(), 4, 31, 'completed', 'Momo', 'url_receipt_44', GETDATE()),
('GG112', 100.00, GETDATE(), 5, 32, 'processing', 'VNPAY', 'url_receipt_55', GETDATE()),
('GG1987', 100.00, GETDATE(),6,30, 'completed', 'VNPAY', 'url_receipt_22', GETDATE()),
('GG149', 150.00, GETDATE(), 5, 31, 'completed', 'Momo', 'url_receipt_234', GETDATE());