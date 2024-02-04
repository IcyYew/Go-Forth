package com.spring.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class Welcome {
    @GetMapping("/")
    public String welcome() {
        return "Welcome to troop battle sim test";
    }
}
