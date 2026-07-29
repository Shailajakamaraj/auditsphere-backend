package com.auditsphere.auditspherebackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDashboardController {

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "Welcome User!";
    }
}