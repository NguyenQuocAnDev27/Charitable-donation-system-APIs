package com.example.DonationInUniversity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @GetMapping("/test")
    public String test() {
        return "success"; 
    }
    @GetMapping("/")
    public String homepage() {
        return "index"; 
    }
    @GetMapping("/test1")
    public String homepage1() {
        return "test"; 
    }
}
