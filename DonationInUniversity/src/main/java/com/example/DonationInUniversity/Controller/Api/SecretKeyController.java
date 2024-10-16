
package com.example.DonationInUniversity.controller.api;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

// Only for create secret key from start server

//@RestController
//@RequestMapping("/api/keys")
//public class SecretKeyController {
//    // Endpoint to generate and return the secure key
//    @GetMapping("/generate")
//    public String generateKey() {
//        // Generate a secure key for HS256
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//        // Encode the key to Base64 for easy storage or transfer
//        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
//    }
//}
