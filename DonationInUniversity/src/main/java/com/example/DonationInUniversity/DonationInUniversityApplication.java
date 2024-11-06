package com.example.DonationInUniversity;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DonationInUniversityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonationInUniversityApplication.class, args);

    }

    @PostConstruct
    public void checkFiles() {
        checkRequiredFile("env.properties");
        checkRequiredFile("credentials.json");
    }

    private void checkRequiredFile(String fileName) {
        try {
            File file = new ClassPathResource(fileName).getFile();
            if (!file.exists()) {
                throw new RuntimeException("Required file " + fileName + " is missing. Stopping application.");
            }
            System.out.println("File " + fileName + " found.");
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while checking " + fileName + ": " + e.getMessage(), e);
        }
    }

}
