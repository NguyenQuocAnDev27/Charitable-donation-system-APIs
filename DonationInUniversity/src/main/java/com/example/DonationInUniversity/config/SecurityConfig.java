package com.example.DonationInUniversity.config;

import com.example.DonationInUniversity.security.ApiRequestFilter;
import com.example.DonationInUniversity.service.CustomUserDetailsService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ApiRequestFilter apiRequestFilter;
    private final CustomUserDetailsService detailsService;

    @Autowired
    public SecurityConfig(@Lazy ApiRequestFilter apiRequestFilter, @Lazy CustomUserDetailsService detailsService) {
        this.apiRequestFilter = apiRequestFilter;
        this.detailsService = detailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF if not required, or configure it as needed
                .csrf(AbstractHttpConfigurer::disable)

                // Define which URLs are permitted and which require authentication
                .authorizeHttpRequests(auth -> auth
                        // Public API endpoints
                        .requestMatchers(
                                "/api/register",
                                "/api/register/admin",
                                "/api/authenticate",
                                "/admin/login",
                                "/register",
                                "/forgot_password",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/token/refresh",
                                "/*",
                                "/admin/**",
                                "/admin/static/**", "/admin/assets/**"
                        ).permitAll()

                        // Admin specific URLs (secured)
//                        .requestMatchers("/admin/**").hasAuthority("admin")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )

                // Add API filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(apiRequestFilter, UsernamePasswordAuthenticationFilter.class)


                .formLogin(login -> login
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                )

                // Remember-me configuration
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember-me")
                        .rememberMeCookieName("remember-me-cookie")
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .key("uniqueAndSecret")
                        .tokenRepository(persistentTokenRepository())
                        .userDetailsService(detailsService)
                )

                // Logout configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/admin/login")
                );

        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // You can use either Sha256PasswordEncoder or BCryptPasswordEncoder depending on your use case
        return new Sha256PasswordEncoder();
    }

}


