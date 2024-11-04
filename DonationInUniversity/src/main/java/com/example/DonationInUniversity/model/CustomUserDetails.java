package com.example.DonationInUniversity.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;


public class CustomUserDetails implements UserDetails, Serializable {

    private VerifiedUser user;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public CustomUserDetails(Optional<VerifiedUser> user, Collection<GrantedAuthority> authorities) {
        super();
    }
    public VerifiedUser getUserModel() {
        return user;
    }
    public void setAccountEntity(VerifiedUser user) {
        this.user = user;
    }
    public CustomUserDetails(VerifiedUser user, Collection<? extends GrantedAuthority> authorities) {
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
    // Getter cho fullName từ đối tượng User
    public String getFullName() {
        return user.getFullName();
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
