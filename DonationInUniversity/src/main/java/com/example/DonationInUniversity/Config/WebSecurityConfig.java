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
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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
                .requestMatchers("/admin/**").hasAuthority("Admin")
                .requestMatchers("/admin/**").hasAuthority("Project Manager")
                .anyRequest().authenticated()
        );
        http.formLogin(login->login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")

        );
        http.rememberMe(rememberMe->rememberMe
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me-cookie")
                .tokenValiditySeconds(7 * 24 * 60 * 60)
                .key("uniqueAndSecret")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(detailsService)
        );
        http.logout(logout->logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );
        return http.build();
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }
    @Bean
    WebSecurityCustomizer customizer() {
        return web -> web.ignoring().requestMatchers("/static/**", "/assets/**");
    }
}
