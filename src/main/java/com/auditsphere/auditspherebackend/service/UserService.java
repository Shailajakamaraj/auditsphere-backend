package com.auditsphere.auditspherebackend.service;

import com.auditsphere.auditspherebackend.dto.*;
import com.auditsphere.auditspherebackend.entity.User;
import com.auditsphere.auditspherebackend.jwt.JwtUtil;
import com.auditsphere.auditspherebackend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    // Constructor Injection
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    // Register User
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {

        User user = new User();

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());

        // Encrypt password before saving
        user.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword())
        );

        user.setRole(userRequestDTO.getRole());


        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }



    // Get All Users
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }



    // Get User By ID
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElse(null);


        if(user == null) {
            return null;
        }


        return convertToDTO(user);
    }



    // Update User
    public User updateUser(Long id, User updatedUser) {


        User existingUser =
                userRepository.findById(id)
                        .orElse(null);


        if(existingUser != null) {


            existingUser.setName(updatedUser.getName());

            existingUser.setEmail(updatedUser.getEmail());


            // Encrypt updated password
            existingUser.setPassword(
                    passwordEncoder.encode(
                            updatedUser.getPassword()
                    )
            );


            existingUser.setRole(updatedUser.getRole());


            return userRepository.save(existingUser);
        }


        return null;
    }




    // Delete User
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }





    // Convert Entity to DTO
    public UserResponseDTO convertToDTO(User user) {


        return new UserResponseDTO(

                user.getId(),

                user.getName(),

                user.getEmail(),

                user.getRole()
        );
    }





    // Login
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {


        Optional<User> optionalUser =
                userRepository.findByEmail(
                        loginRequestDTO.getEmail()
                );



        if(optionalUser.isEmpty()) {


            return new LoginResponseDTO(
                    "Invalid Email or Password",
                    null
            );
        }



        User user = optionalUser.get();



        // Compare entered password with encrypted password
        if(!passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                user.getPassword()
        )) {


            return new LoginResponseDTO(
                    "Invalid Email or Password",
                    null
            );
        }



        // Generate JWT Token
        String token =
                jwtUtil.generateToken(user.getEmail());
        System.out.println("Generated Token: " + token);


        return new LoginResponseDTO(
                "Login Successful",
                token
        );
    }

}