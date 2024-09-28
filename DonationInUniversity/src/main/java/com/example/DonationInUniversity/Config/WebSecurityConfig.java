package com.example.DonationInUniversity.Config;
import com.example.DonationInUniversity.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomUserDetailsService detailsService;
    @Bean
    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable());
        http.authorizeHttpRequests(auth->auth
                .requestMatchers("/*").permitAll()
                .requestMatchers("/admin/**").hasAuthority("admin")
                .requestMatchers("/admin/**").hasAuthority("project_manager")
                .anyRequest().authenticated()
        );
        http.formLogin(login->login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")

        );
        return http.build();
    }

    @Bean
    WebSecurityCustomizer customizer() {
        return web -> web.ignoring().requestMatchers("/static/**", "/assets/**");
    }
}
