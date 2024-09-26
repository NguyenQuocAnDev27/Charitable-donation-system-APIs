package com.example.DonationInUniversity.Service;

import com.example.DonationInUniversity.Dto.CreateUserDTO;
import com.example.DonationInUniversity.Dto.UserDTO;
import com.example.DonationInUniversity.Exception.UserNotFoundException;
import com.example.DonationInUniversity.Model.User;
import com.example.DonationInUniversity.Repository.UserRepository;
import com.example.DonationInUniversity.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Convert Entity to UserDTO for retrieval (including createdAt and updatedAt)
    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getFullName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getPhoneNumber(),
                user.getRoleId(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


    // Create a new user
    public UserDTO createUser(CreateUserDTO userDto) {
        userRepository.save(new User(
                userDto.getFullName(),
                userDto.getEmail(),
                Utils.hashPassword(userDto.getPassword()),
                userDto.getPhoneNumber(),
                userDto.getRoleId(),
                new Date(),
                new Date()
        ));
        return new UserDTO(
                userDto.getFullName(),
                userDto.getEmail(),
                Utils.hashPassword(userDto.getPassword()),
                userDto.getPhoneNumber(),
                userDto.getRoleId(),
                new Date(),
                new Date()
        );
    }


    // Get all users (using findAll)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get user by ID
    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return convertToDto(user);
    }

    // Update an existing user
    public UserDTO updateUser(int id, UserDTO userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setFullName(userDetails.getFullName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());
        existingUser.setRoleId(userDetails.getRoleId());
        existingUser.setUpdatedAt(new Date());

        userRepository.save(existingUser);
        return convertToDto(existingUser);
    }

    // Delete a user
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
