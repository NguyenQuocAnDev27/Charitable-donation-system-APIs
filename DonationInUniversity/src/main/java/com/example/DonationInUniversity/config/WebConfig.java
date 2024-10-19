package com.example.DonationInUniversity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình để truy cập vào thư mục images/project_detail bên ngoài src
        registry.addResourceHandler("/images/project_detail/**")
                .addResourceLocations("file:./images/project_detail/");
    }
}
