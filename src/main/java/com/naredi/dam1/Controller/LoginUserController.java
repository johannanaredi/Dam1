package com.naredi.dam1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mega")
public class LoginUserController {

    @GetMapping("/user")
    public String userHello() {
        return "Hej användare!";
    }

}

