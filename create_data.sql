USE sgu_charity;

-- Insert into Donation_Projects
INSERT INTO `Donation_Projects` 
(`project_name`,
 `description`,
 `goal_amount`,
 `current_amount`,
 `start_date`,
 `end_date`,
 `status`,
 `project_manager_id`,
 `is_deleted`)
VALUES
('Chuyến xe ước mơ',
 'Tặng quà trẻ em có hoàn cảnh khó khăn năm học 2024 – 2025 tại miền Tây Nam Bộ.',
 123000000,
 0, 
 '2024-11-24',
 '2025-02-24', -- 4 months duration
 'pending',
 2, 
 1),
('Cơm có thịt',
 'Cải thiện dinh dưỡng và khuyến học cho trẻ em vùng cao thông qua chương trình “Một triệu bữa cơm có thịt”.',
 100000000,
 0, 
 '2024-11-24',
 '2025-02-24', -- 4 months duration
 'pending',
 2, 
 1),
('Đông Ấm Cho Em',
 'Cung cấp áo ấm, nhu yếu phẩm, và hỗ trợ mùa đông cho trẻ em vùng cao.',
 50000000,
 0, 
 '2024-11-24',
 '2025-06-24', -- 7 months duration
 'pending',
 2, 
 1),
('Cánh Diều Hy Vọng',
 'Gây quỹ y tế hỗ trợ trẻ em mắc dị tật bẩm sinh và gặp tai nạn.',
 80000000,
 0, 
 '2024-11-24',
 '2025-11-24', -- 12 months duration
 'pending',
 2, 
 1),
('Ghép đôi Trăng tròn',
 'Lan tỏa thông điệp yêu thương và sẻ chia nhân dịp Tết Trung Thu.',
 30000000,
 0, 
 '2024-08-15',
 '2024-12-15', -- 4 months duration
 'pending',
 2, 
 1),
('Quỹ Hy vọng cùng đồng bào vượt bão',
 'Hỗ trợ các khu vực chịu thiệt hại nặng nề sau bão Yagi bằng các chương trình cứu trợ khẩn cấp.',
 150000000,
 0, 
 '2024-09-10',
 '2025-01-10', -- 4 months duration
 'pending',
 2, 
 1);

INSERT INTO `Project_Detail_Text` 
(`project_id`, `content`, `is_deleted`, `display_order`)
VALUES
-- Chuyến xe ước mơ (project_id = 7)
(7, 'Chương trình đã trao 100 phần quà, mỗi phần trị giá 250.000 đồng bao gồm sách, vở và dụng cụ học tập, tạo điều kiện tốt hơn cho các em thiếu nhi trước dịp bước vào năm học mới.', TRUE, 1),
(7, 'Ban tổ chức tặng quà cho thiếu nhi thị trấn Rạch Gòi, huyện Châu Thành A, tỉnh Hậu Giang.', TRUE, 2),
(7, 'Ngày 24/7, Trung tâm Hỗ trợ học sinh, sinh viên thành phố Cần Thơ phối hợp cùng Huyện đoàn Châu Thành A, tỉnh Hậu Giang tổ chức Chương trình “Chuyến xe ước mơ”, tặng quà cho trẻ em có hoàn cảnh khó khăn năm học 2024 – 2025, tại thị trấn Rạch Gòi, huyện Châu Thành A, tỉnh Hậu Giang.', TRUE, 3),
(7, 'Trong thời gian tới, Chương trình “Chuyến xe ước mơ” sẽ tiếp tục mang khoảng 450 phần quà thiết thực với tổng giá trị gần 123 triệu đồng, đến với thiếu nhi tại huyện Giồng Riềng, tỉnh Kiên Giang; quận Ô Môn và quận Ninh Kiều, thành phố Cần Thơ.', TRUE, 4),
(7, 'Chương trình “Chuyến xe ước mơ” được Hội đồng Đội thành phố Cần Thơ và Trung tâm Hỗ trợ học sinh, sinh viên thành phố khởi động lần đầu tiên vào năm học 2020 – 2021.', TRUE, 5),
(7, 'Bên cạnh đó, chương trình cũng tích hợp nhiều hoạt động chăm sóc sức khỏe và tạo sân chơi cho thiếu nhi ở mỗi chuyến tặng quà, góp phần chăm lo trẻ em một cách toàn diện.', TRUE, 6),

-- Cơm có thịt (project_id = 8)
(8, 'Năm 2020, Ngân hàng Thế giới (WB) đã xếp Việt Nam là 1 trong 34 quốc gia trên thế giới đang đối mặt với gánh nặng suy dinh dưỡng.', TRUE, 1),
(8, 'Viện Dinh dưỡng Quốc gia cũng từng nhận định, tại vùng núi phía Bắc và nhiều vùng dân tộc thiểu số khác, có hơn 70% trẻ em chưa được ăn đúng, ăn đủ.', TRUE, 2),
(8, 'Mặc dù tỉ lệ suy dinh dưỡng toàn quốc đã được cải thiện trong những năm gần đây, tỉ lệ thấp còi ở trẻ em là người dân tộc thiểu số (31,4%) vẫn cao gấp 2 lần nhóm trẻ là người Kinh.', TRUE, 3),
(8, 'Trong nhiều năm qua, đã có những tổ chức, doanh nghiệp và cá nhân đã quan tâm và chung tay đóng góp với nhiều hoạt động ý nghĩa và thiết thực.', TRUE, 4),
(8, 'Quỹ Trò nghèo vùng cao, tiền thân là chương trình từ thiện “Cơm Có Thịt”, đã triển khai nhiều hoạt động từ năm 2011.', TRUE, 5),
(8, 'Chương trình hỗ trợ hơn 100 trường vùng cao, với trên 10.000 em nhỏ nhận tiền hỗ trợ cải thiện bữa ăn bán trú.', TRUE, 6),
(8, '“Một triệu bữa cơm có thịt” khởi động vào tháng 9.2023 tại trường phổ thông dân tộc bán trú THCS Măng Cành.', TRUE, 7),

-- Đông Ấm Cho Em (project_id = 9)
(9, 'Mùa đông ở vùng cao Việt Nam, đặc biệt là các tỉnh phía Bắc, thường rất lạnh và khắc nghiệt.', TRUE, 1),
(9, '“Đông Ấm Cho Em” là một sáng kiến từ thiện thường niên, quyên góp áo ấm, chăn mền, sách vở và nhu yếu phẩm thiết yếu.', TRUE, 2),
(9, 'Chương trình đã mang lại niềm vui, sự an toàn và niềm hy vọng cho hàng nghìn trẻ em vùng cao mỗi năm.', TRUE, 3),

-- Cánh Diều Hy Vọng (project_id = 10)
(10, 'Chiến dịch gây quỹ “Cánh Diều Hy Vọng, Hè 2024” do Quỹ Nâng Bước Tuổi Thơ tổ chức nhằm hỗ trợ trẻ em có được sự chăm sóc y tế chất lượng cao kịp thời.', TRUE, 1),
(10, 'Quỹ Nâng Bước Tuổi Thơ đã giúp hơn 800 trẻ em dị tật bẩm sinh và thương tật do tai nạn được điều trị tại các cơ sở y tế tốt nhất Việt Nam.', TRUE, 2),

-- Ghép đôi Trăng tròn (project_id = 11)
(11, '“Ghép đôi Trăng tròn” kêu gọi cộng đồng quan tâm nhiều hơn đến những cuộc đời xung quanh với tình yêu thương.', TRUE, 1),
(11, 'Chương trình đã tạo điều kiện cho mọi người dân tham gia các hoạt động xã hội ý nghĩa.', TRUE, 2),

-- Quỹ Hy vọng cùng đồng bào vượt bão (project_id = 12)
(12, 'Yagi là cơn bão mạnh nhất 30 năm qua, gây ngập lụt và sạt lở trên diện rộng.', TRUE, 1),
(12, 'Bão đã làm 65 người chết, 40 người mất tích, và hàng nghìn ngôi nhà bị hư hại nặng.', TRUE, 2),
(12, 'Mọi đóng góp dù nhỏ nhất đều có ý nghĩa rất lớn trong lúc này. Chúng tôi cam kết trực tiếp điều phối, minh bạch mọi thông tin đóng góp.', TRUE, 3);
