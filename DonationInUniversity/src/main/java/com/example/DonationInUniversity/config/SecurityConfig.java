package com.example.DonationInUniversity.config;

import com.example.DonationInUniversity.security.ApiRequestFilter;
import com.example.DonationInUniversity.utils.Sha256PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ApiRequestFilter apiRequestFilter;

    @Autowired
    public SecurityConfig(@Lazy ApiRequestFilter apiRequestFilter) {
        this.apiRequestFilter = apiRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/register",
                                "/api/register/admin",
                                "/api/authenticate",
                                "/login",
                                "/register",
                                "/forgot_password",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/token/refresh"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(apiRequestFilter, UsernamePasswordAuthenticationFilter.class);
        // "/api/keys/generate"
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Sha256PasswordEncoder();
    }
}


