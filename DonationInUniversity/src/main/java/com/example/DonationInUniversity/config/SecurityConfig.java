package com.example.DonationInUniversity.config;

import com.example.DonationInUniversity.exception.CustomAccessDeniedHandler;
import com.example.DonationInUniversity.exception.GlobalExceptionHandler;
import com.example.DonationInUniversity.security.ApiRequestFilter;
import com.example.DonationInUniversity.utils.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApiRequestFilter apiRequestFilter;
    private final CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    public SecurityConfig(ApiRequestFilter apiRequestFilter, CustomAuthenticationSuccessHandler successHandler) {
        this.apiRequestFilter = apiRequestFilter;
        this.successHandler = successHandler;
    }

    /**
     * Password encoder bean for hashing passwords using BCrypt algorithm.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures security for the API endpoints that start with /api/**.
     * - Disables CSRF for stateless API requests.
     * - Configures CORS.
     * - Stateless session management.
     * - Allows unauthenticated access for certain public API endpoints.
     * - Adds custom Jwt-based ApiRequestFilter.
     *
     * @param http HttpSecurity configuration object
     * @return a configured SecurityFilterChain for API requests
     * @throws Exception in case of any configuration errors
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/register",
                                "/api/authenticate",
                                "/api/token/refresh",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/projects/page", // for guest
                                "/api/project_detail/image/**" // for images
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(apiRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Configures security for the admin interface.
     * - Disables CSRF for admin endpoints.
     * - Configures CORS.
     * - Requires sessions for admin requests.
     * - Allows unauthenticated access to login and register pages.
     * - Requires "admin" authority for other admin endpoints.
     * - Configures form login for admin access.
     *
     * @param http HttpSecurity configuration object
     * @return a configured SecurityFilterChain for admin requests
     * @throws Exception in case of any configuration errors
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http, GlobalExceptionHandler globalExceptionHandler, CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception {
        http
                .securityMatcher("/admin/**", "/manager/**","/admin/login", "/admin/logout")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login","/admin/403").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("admin")
                        .requestMatchers("/manager/**").hasAuthority("project_manager")
                        .anyRequest().authenticated()

                )


                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/doLogin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureUrl("/admin/login?error=true")

                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler))
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                );

        return http.build();
    }

    /**
     * Provides the AuthenticationManager bean for managing authentication processes.
     *
     * @param authenticationConfiguration the AuthenticationConfiguration object
     * @return an AuthenticationManager instance
     * @throws Exception in case of configuration errors
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures CORS to allow specific origins, HTTP methods, and headers.
     *
     * @return a configured UrlBasedCorsConfigurationSource
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://192.168.1.171:3000")); // Allowed origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Allowed headers
        configuration.setAllowCredentials(true); // Allow credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Ignores security checks for static resources.
     *
     * @return a WebSecurityCustomizer instance
     */
    @Bean
    WebSecurityCustomizer customizer() {
        return web -> web.ignoring().requestMatchers("/static/**", "/assets/**");
    }
}
