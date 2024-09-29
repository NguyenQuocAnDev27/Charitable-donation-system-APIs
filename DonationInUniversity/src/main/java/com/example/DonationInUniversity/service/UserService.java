package com.example.DonationInUniversity.service;

import com.example.DonationInUniversity.controller.api.UserController;
import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.VerifiedUser;
import com.example.DonationInUniversity.repository.RoleRepository;
import com.example.DonationInUniversity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService (UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Save a new user with a specific role
    public VerifiedUser saveUser(VerifiedUser user, String roleName) {
        // Find role by role name
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        // Encrypt password and assign role
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole(role);

        return userRepository.save(user);
    }

    // Find user by email
    public Optional<VerifiedUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Implementation of UserDetailsService for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
