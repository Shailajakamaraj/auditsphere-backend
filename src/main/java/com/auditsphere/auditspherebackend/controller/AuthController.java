package com.auditsphere.auditspherebackend.controller;


import com.auditsphere.auditspherebackend.dto.LoginRequestDTO;
import com.auditsphere.auditspherebackend.dto.LoginResponseDTO;
import com.auditsphere.auditspherebackend.dto.UserRequestDTO;
import com.auditsphere.auditspherebackend.dto.UserResponseDTO;
import com.auditsphere.auditspherebackend.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:5176"
        }
)
public class AuthController {



    private final UserService userService;



    public AuthController(
            UserService userService
    ){

        this.userService = userService;

    }







    // REGISTER USER

    @PostMapping("/register")
    public UserResponseDTO register(
            @Valid @RequestBody UserRequestDTO request
    ){

        return userService.saveUser(request);

    }







    // LOGIN USER

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO request
    ){

        return userService.login(request);

    }


}