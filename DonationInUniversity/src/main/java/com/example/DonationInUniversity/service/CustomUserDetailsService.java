package com.example.DonationInUniversity.service;

import com.example.DonationInUniversity.model.CustomUserDetails;
import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private LoginService loginService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = loginService.loginAdmin(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        Role role = user.getRole();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        return new CustomUserDetails(user, authorities);
    }
}
