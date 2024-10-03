//package com.example.DonationInUniversity.config;
//import com.example.DonationInUniversity.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//
//@Configuration
//@EnableWebSecurity
//@Order(2)
//public class WebSecurityConfig {
//    @Autowired
//    private CustomUserDetailsService detailsService;
//    @Bean
//    BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    SecurityFilterChain securityFilterChainAdmin (HttpSecurity http) throws Exception{
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.authorizeHttpRequests(auth->auth
//                .requestMatchers("/admin/login").permitAll()
//                .requestMatchers("/admin/**").hasAuthority("admin")
//                .anyRequest().authenticated()
//        );
//        http.formLogin(login->login
//                .loginPage("/admin/login")
//                .loginProcessingUrl("/admin/login")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/admin/**")
//
//        );
//        http.rememberMe(rememberMe->rememberMe
//                .rememberMeParameter("remember-me")
//                .rememberMeCookieName("remember-me-cookie")
//                .tokenValiditySeconds(7 * 24 * 60 * 60)
//                .key("uniqueAndSecret")
//                .tokenRepository(persistentTokenRepository())
//                .userDetailsService(detailsService)
//        );
//        http.logout(logout->logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//        );
//        return http.build();
//    }
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        return new InMemoryTokenRepositoryImpl();
//    }
//    @Bean
//    WebSecurityCustomizer customizer() {
//        return web -> web.ignoring().requestMatchers("/static/**", "/assets/**");
//    }
//}
