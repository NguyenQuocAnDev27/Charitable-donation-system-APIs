package com.example.DonationInUniversity.service;

import com.example.DonationInUniversity.model.RefreshToken;
import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.UserProfile;
import com.example.DonationInUniversity.model.VerifiedUser;
import com.example.DonationInUniversity.repository.RefreshTokenRepository;
import com.example.DonationInUniversity.repository.RoleRepository;
import com.example.DonationInUniversity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
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

    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Implementation of UserDetailsService for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    // Convert VerifiedUser to UserProfile
    public UserProfile convertToUserProfile(VerifiedUser user) {
        return new UserProfile(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().getRoleName(), // Only roleName is passed
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isEnabled()
        );
    }

    public void saveRefreshToken(int userId, String refreshToken) {
        logger.info("Saving refresh token for userId: {}", userId);

        // Tìm đối tượng VerifiedUser từ userId
        VerifiedUser user = userRepository.findById((long) userId)
                .orElseThrow(() -> {
                    logger.error("User not found for userId: {}", userId);
                    return new RuntimeException("User not found");
                });

        logger.info("User found: {}. Creating refresh token.", user.getEmail());

        // Tạo đối tượng RefreshToken và thiết lập giá trị
        RefreshToken token = new RefreshToken();
        token.setUser(user);  // Sử dụng đối tượng VerifiedUser thay vì userId
        token.setToken(refreshToken);
        token.setExpiresAt(LocalDateTime.now().plusDays(7));  // 7 days expiration

        // Lưu refresh token vào database
        refreshTokenRepository.save(token);
        logger.info("Refresh token saved successfully for userId: {}", userId);
    }

    public boolean isRefreshTokenValid(String token) {
        logger.info("Checking if refresh token is valid: {}", token);

        boolean isValid = refreshTokenRepository.findByToken(token)
                .filter(refreshToken -> {
                    boolean isNotExpired = refreshToken.getExpiresAt().isAfter(LocalDateTime.now());
                    logger.info("Token expiration check: {}. Expires at: {}", isNotExpired, refreshToken.getExpiresAt());
                    return isNotExpired;
                })
                .isPresent(); // Trả về true nếu token hợp lệ, false nếu không

        logger.info("Refresh token validity: {}", isValid);
        return isValid;
    }

    public Optional<VerifiedUser> findByRefreshToken(String refreshToken) {
        logger.info("Finding user by refresh token: {}", refreshToken);

        return refreshTokenRepository.findByToken(refreshToken)
                .map(refreshTokenEntity -> {
                    VerifiedUser user = refreshTokenEntity.getUser();
                    logger.info("User found for refresh token: {}", user.getEmail());
                    return user;
                });
    }

    public void deleteRefreshToken(String refreshToken) {
        logger.info("Deleting refresh token: {}", refreshToken);

        refreshTokenRepository.findByToken(refreshToken)
                .ifPresentOrElse(refreshTokenEntity -> {
                    refreshTokenRepository.delete(refreshTokenEntity);
                    logger.info("Refresh token deleted: {}", refreshToken);
                }, () -> {
                    logger.warn("Refresh token not found: {}", refreshToken);
                });
    }
}
