package com.example.DonationInUniversity.exception;

import com.example.DonationInUniversity.model.MyCustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MyCustomResponse<?>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        MyCustomResponse<String> errorResponse = new MyCustomResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "The requested API endpoint is not available.",
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    // You can add other exception handlers here if needed
}
