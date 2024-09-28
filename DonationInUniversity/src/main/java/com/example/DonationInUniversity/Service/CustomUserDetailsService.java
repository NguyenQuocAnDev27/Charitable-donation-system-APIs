package com.example.DonationInUniversity.Service;

import com.example.DonationInUniversity.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
        return null;
    }
}
