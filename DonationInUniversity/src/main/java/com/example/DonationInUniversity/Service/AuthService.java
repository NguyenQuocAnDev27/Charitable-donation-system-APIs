package com.example.DonationInUniversity.Service;

import com.example.DonationInUniversity.Dto.AuthDTO;
import com.example.DonationInUniversity.Model.User;
import com.example.DonationInUniversity.Repository.AuthRepository;
import com.example.DonationInUniversity.Utils.JwtUtils;
import com.example.DonationInUniversity.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtUtils jwtUtils;

    // Method to verify account and return AuthDTO
    public AuthDTO login(String email, String password) {
        // Hash the incoming password to compare with the stored hash
        String passwordHash = Utils.hashPassword(password);

        // Verify the account using the repository query
        List<User> ls_user = authRepository.verifyAccount(email, passwordHash);
        User user = ls_user.get(0);

        // If the user is found, generate a JWT token and return an AuthDTO
        if (user != null) {
            String jwtToken = jwtUtils.generateToken(user.getEmail());

            // Map User entity to AuthDTO
            return new AuthDTO(
                    user.getUserId() + "",
                    user.getFullName(),
                    user.getEmail(),
                    jwtToken,
                    user.getPhoneNumber(),
                    user.getRoleId()
            );
        }

        // Return null or throw an exception if authentication fails
        return null;
    }
}
