package com.example.DonationInUniversity.service.api;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.repository.RefreshTokenRepository;
import com.example.DonationInUniversity.repository.RoleRepository;
import com.example.DonationInUniversity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            RefreshTokenRepository refreshTokenRepository,
            LoginService loginService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.loginService = loginService;
    }

    /**
     * Saves a new user and assigns a specific role to the user.
     * Encrypts the user's password before saving.
     *
     * @param user the user to save
     * @param roleName the name of the role to assign to the user
     * @return the saved VerifiedUser
     * @throws RuntimeException if the role is not found
     */
    public VerifiedUser saveUser(VerifiedUser user, String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

//        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
//        user.setPasswordHash(encodedPassword);

        user.setRole(role);
        return userRepository.save(user);
    }

    /**
     * Finds a user by email.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, otherwise empty
     */
    public Optional<VerifiedUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Checks if an email is already registered in the system.
     *
     * @param email the email to check
     * @return true if the email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Loads a user by email. Used for authentication in Spring Security.
     *
     * @param email the email of the user
     * @return UserDetails for the user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = loginService.loginAdmin(email);
        if (user == null) {
            logger.error("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Role role = user.getRole();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        return new CustomUserDetails(user, authorities);
    }

    /**
     * Converts a VerifiedUser entity to a UserProfile object.
     * Useful for returning user details without exposing the internal model.
     *
     * @param user the user to convert
     * @return a UserProfile object containing user's public details
     */
    public UserProfile convertToUserProfile(VerifiedUser user) {
        return new UserProfile(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().getRoleName(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isEnabled()
        );
    }

    /**
     * Saves a refresh token for a user.
     * Creates a new RefreshToken entity and associates it with the user.
     *
     * @param userId the ID of the user
     * @param refreshToken the refresh token to save
     * @throws RuntimeException if the user is not found
     */
    public void saveRefreshToken(int userId, String refreshToken) {
        logger.info("Saving refresh token for userId: {}", userId);

        VerifiedUser user = userRepository.findById((long) userId)
                .orElseThrow(() -> {
                    logger.error("User not found for userId: {}", userId);
                    return new RuntimeException("User not found");
                });

        logger.info("User found: {}. Creating refresh token.", user.getEmail());

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(refreshToken);
        token.setExpiresAt(LocalDateTime.now().plusDays(7));  // 7 days expiration

        refreshTokenRepository.save(token);
        logger.info("Refresh token saved successfully for userId: {}", userId);
    }

    /**
     * Checks if a refresh token is valid.
     * A token is considered valid if it exists and has not expired.
     *
     * @param token the refresh token to check
     * @return true if the token is valid, false otherwise
     */
    public boolean isRefreshTokenValid(String token) {
        logger.info("Checking if refresh token is valid: {}", token);

        Optional<RefreshToken> optionalToken = refreshTokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            logger.warn("Refresh token not found: {}", token);
            return false;  // Token not found
        }

        RefreshToken refreshToken = optionalToken.get();

        if (isRefreshTokenExpired(refreshToken)) {
            logger.warn("Refresh token is expired: {}. Expires at: {}", token, refreshToken.getExpiresAt());
            return false;  // Token expired
        }

        logger.info("Refresh token is valid: {}", token);
        return true;
    }

    /**
     * Checks if a refresh token has expired.
     *
     * @param refreshToken the token to check
     * @return true if the token has expired, false otherwise
     */
    public boolean isRefreshTokenExpired(RefreshToken refreshToken) {
        boolean isNotExpired = refreshToken.getExpiresAt().isAfter(LocalDateTime.now());
        logger.info("Token expiration check: {}. Expires at: {}", isNotExpired, refreshToken.getExpiresAt());
        return !isNotExpired;
    }

    /**
     * Finds a VerifiedUser by their refresh token.
     *
     * @param refreshToken the refresh token to use for finding the user
     * @return an Optional containing the VerifiedUser if found, otherwise empty
     */
    public Optional<VerifiedUser> findByRefreshToken(String refreshToken) {
        logger.info("Finding user by refresh token: {}", refreshToken);

        return refreshTokenRepository.findByToken(refreshToken)
                .map(refreshTokenEntity -> {
                    VerifiedUser user = refreshTokenEntity.getUser();
                    logger.info("User found for refresh token: {}", user.getEmail());
                    return user;
                });
    }

    /**
     * Deletes a refresh token from the repository.
     *
     * @param refreshToken the refresh token to delete
     */
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
