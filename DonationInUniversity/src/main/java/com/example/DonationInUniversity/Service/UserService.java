package com.example.DonationInUniversity.Service;

import com.example.DonationInUniversity.Dto.UserDTO;
import com.example.DonationInUniversity.Exception.MyException;
import com.example.DonationInUniversity.Exception.UserNotFoundException;
import com.example.DonationInUniversity.Model.User;
import com.example.DonationInUniversity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Convert Entity to DTO
    private UserDTO convertToDto(User user) {
        return new UserDTO(user.getUserId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getRoleId());
    }

    // Convert DTO to Entity
    private User convertToEntity(UserDTO userDTO) {
        return new User(
                userDTO.getUserId(),
                userDTO.getFullName(),
                userDTO.getEmail(),
                userDTO.getPhoneNumber(),
                userDTO.getRoleId(),
                null,   // createdAt is null for new user
                null    // updatedAt will be automatically managed
        );
    }

    // Get all users (using findAll)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get users by role ID
    public List<UserDTO> getUsersByRoleId(int roleId) {
        return userRepository.findUsersByRoleId(roleId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get user by ID
    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return convertToDto(user);
    }

    // Create a new user
    public UserDTO createUser(UserDTO userDto) {
        User newUser = convertToEntity(userDto);
        userRepository.save(newUser);
        return convertToDto(newUser);
    }

    // Update an existing user
    public UserDTO updateUser(int id, UserDTO userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setFullName(userDetails.getFullName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());
        existingUser.setRoleId(userDetails.getRoleId());

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
