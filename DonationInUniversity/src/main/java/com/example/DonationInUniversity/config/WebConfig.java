package com.example.DonationInUniversity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đường dẫn tới thư mục images
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:./images/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}