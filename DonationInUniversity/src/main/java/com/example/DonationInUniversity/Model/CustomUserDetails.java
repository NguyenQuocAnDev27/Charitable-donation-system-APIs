package com.example.DonationInUniversity.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {

    private User user;
    private Collection<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public CustomUserDetails() {
        super();
    }
    public User getUserModel() {
        return user;
    }
    public void setAccountEntity(User user) {
        this.user = user;
    }
    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.user = user;
        this.authorities = authorities;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
