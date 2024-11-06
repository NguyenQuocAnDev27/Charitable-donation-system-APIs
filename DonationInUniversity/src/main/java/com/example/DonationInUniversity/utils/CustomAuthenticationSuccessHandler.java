package com.example.DonationInUniversity.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Lấy các quyền (roles) của người dùng
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Kiểm tra quyền và chuyển hướng
        if (roles.contains("admin")) {
            response.sendRedirect("/admin/"); // Chuyển hướng đến /admin nếu là admin
        } else if (roles.contains("project_manager")) {
            response.sendRedirect("/manager"); // Chuyển hướng đến /manager nếu là project manager
        }
    }
}
