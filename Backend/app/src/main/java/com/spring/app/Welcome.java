package com.spring.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Welcome controller class.
 * @author Michael Geltz
 */
@RestController
public class Welcome {

    /**
     * Temporary welcome simulation.
     * @return Returns a specific string.
     */
    @GetMapping("/")
    public String printWelcome() {
        return "Welcome to troop battle sim temporary";
    }
}

