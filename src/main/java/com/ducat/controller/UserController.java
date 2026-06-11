package com.ducat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ducat.entity.User;
import com.ducat.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/register")
    public String showRegister(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {

        boolean status = service.register(user);

        // User already exists
        if(!status) {

            model.addAttribute("message",
                    "User Already Exists");

            return "register";
        }

        // Successful registration
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/")
    public String home() {

        return "home";
    }
}