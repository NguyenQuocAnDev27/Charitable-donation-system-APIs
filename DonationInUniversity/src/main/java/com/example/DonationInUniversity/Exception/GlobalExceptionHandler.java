package com.example.DonationInUniversity.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity<String> handleMyException(MyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException ex) {
        // Xuất lỗi ra console
        System.out.println("Authentication error: " + ex.getMessage());

        // Redirect về trang login với thông báo lỗi
        return "redirect:/login?error=true";
    }
    // Handle other exceptions...
}
