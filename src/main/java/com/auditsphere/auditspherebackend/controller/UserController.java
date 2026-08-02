package com.auditsphere.auditspherebackend.controller;
import org.springframework.web.multipart.MultipartFile;

import com.auditsphere.auditspherebackend.dto.UserRequestDTO;
import com.auditsphere.auditspherebackend.dto.UserResponseDTO;
import com.auditsphere.auditspherebackend.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/admin/users")
public class UserController {



    private final UserService userService;



    public UserController(
            UserService userService
    ){

        this.userService = userService;

    }







    // ==============================
    // CREATE USER
    // ==============================

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO createUser(
            @RequestBody UserRequestDTO dto
    ){

        return userService.createAdminUser(dto);

    }









    // ==============================
    // GET ALL USERS
    // ==============================

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUsers(){

        return userService.getAllUsers();

    }









    // ==============================
    // GET USER BY ID
    // ==============================

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getUserById(
            @PathVariable Long id
    ){

        return userService.getUserById(id);

    }









    // ==============================
    // UPDATE USER
    // ==============================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO dto
    ){

        return userService.updateUser(
                id,
                dto
        );

    }









    // ==============================
    // DELETE USER
    // ==============================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(
            @PathVariable Long id
    ){

        userService.deleteUser(id);

        return "User deleted successfully";

    }
// ==============================
// BULK UPLOAD USERS
// ==============================

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public String uploadUsers(
            @RequestParam("file") MultipartFile file
    ) {

        userService.uploadUsersFromExcel(file);

        return "Users uploaded successfully.";

    }

}