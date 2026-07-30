package com.auditsphere.auditspherebackend.controller;


import com.auditsphere.auditspherebackend.dto.LoginRequestDTO;
import com.auditsphere.auditspherebackend.dto.LoginResponseDTO;
import com.auditsphere.auditspherebackend.dto.UserRequestDTO;
import com.auditsphere.auditspherebackend.dto.UserResponseDTO;

import com.auditsphere.auditspherebackend.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
public class AuthController {



    @Autowired
    private UserService userService;





    // Public registration

    @PostMapping("/register")
    public UserResponseDTO register(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ){

        return userService.saveUser(userRequestDTO);

    }






    // Public login

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ){

        return userService.login(loginRequestDTO);

    }


}