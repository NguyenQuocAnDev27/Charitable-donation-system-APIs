package com.example.DonationInUniversity.Service;

import com.example.DonationInUniversity.Model.User;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    private Map<Integer, User> users = new HashMap<>();
    private int userIdCounter = 1;

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public User createUser(User user) {
        user.setUserId(userIdCounter++);
        users.put(user.getUserId(), user);
        return user;
    }

    public User updateUser(int id, User userDetails) {
        User user = users.get(id);
        if (user != null) {
            user.setFullName(userDetails.getFullName());
            user.setEmail(userDetails.getEmail());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setPasswordHash(userDetails.getPasswordHash());
            user.setRoleId(userDetails.getRoleId());
            user.setUpdatedAt(new Date());
        }
        return user;
    }

    public void deleteUser(int id) {
        users.remove(id);
    }
}
