package org.comrades.springtime.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aunt/**")
public class AuthorizationController {

    @GetMapping()
    public String start() {
        return "";
    }

    @PostMapping("/register")
    public String register() {
        return "suck";
    }

    @PostMapping("/signin")
    public void signIn() {

    }

}
