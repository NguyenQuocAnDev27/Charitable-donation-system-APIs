package com.example.DonationInUniversity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DonationInUniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonationInUniversityApplication.class, args);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "1234567890"; // Mật khẩu gốc
		String encodedPassword = passwordEncoder.encode(rawPassword); // Mật khẩu đã mã hóa

		System.out.println("Encoded password: " + encodedPassword);
	}


}
