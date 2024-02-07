<<<<<<< HEAD
package com.spring.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {


    @GetMapping("/")
    public String printWelcome() {
        return "Welcome to troop battle sim temporary";
    }
}
=======
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
>>>>>>> 95d6935c0edc44060bfcf3086af22c741e8c44f1
