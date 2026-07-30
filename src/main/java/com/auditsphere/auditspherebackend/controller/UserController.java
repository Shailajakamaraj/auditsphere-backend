package com.auditsphere.auditspherebackend.controller;


import com.auditsphere.auditspherebackend.dto.UserResponseDTO;
import com.auditsphere.auditspherebackend.entity.User;
import com.auditsphere.auditspherebackend.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/admin/users")
public class UserController {


    private final UserService userService;



    public UserController(UserService userService){

        this.userService = userService;

    }





    // ADMIN creates users

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO createUser(
            @RequestBody User user
    ){

        return userService.createAdminUser(user);

    }





    // ADMIN views all users

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers(){

        return userService.getAllUsers();

    }





    // ADMIN views user by id

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getUserById(
            @PathVariable Long id
    ){

        return userService.getUserById(id);

    }





    // ADMIN updates users

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user
    ){

        return userService.updateUser(id,user);

    }





    // ADMIN deletes users

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(
            @PathVariable Long id
    ){

        userService.deleteUser(id);

        return "User deleted successfully";

    }


}