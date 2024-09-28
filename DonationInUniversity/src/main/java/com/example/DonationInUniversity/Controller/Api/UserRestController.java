//package com.example.DonationInUniversity.Controller.Api;
//
//import com.example.DonationInUniversity.Dto.UserDTO;
//import com.example.DonationInUniversity.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserRestController {
//
//    @Autowired
//    private UserService userService;
//
//    // Get all users
//    @GetMapping
//    public List<UserDTO> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    // Get a single user by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
//        UserDTO user = userService.getUserById(id);
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    // Create a new user
//    @PostMapping
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
//        UserDTO createdUser = userService.createUser(userDTO);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }
//
//    // Update an existing user
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
//        UserDTO updatedUser = userService.updateUser(id, userDTO);
//        if (updatedUser == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }
//
//    // Delete a user
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
