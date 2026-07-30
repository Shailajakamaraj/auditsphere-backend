package com.auditsphere.auditspherebackend.service;

import com.auditsphere.auditspherebackend.dto.LoginRequestDTO;
import com.auditsphere.auditspherebackend.dto.LoginResponseDTO;
import com.auditsphere.auditspherebackend.dto.UserRequestDTO;
import com.auditsphere.auditspherebackend.dto.UserResponseDTO;
import com.auditsphere.auditspherebackend.entity.Role;
import com.auditsphere.auditspherebackend.entity.User;
import com.auditsphere.auditspherebackend.jwt.JwtUtil;
import com.auditsphere.auditspherebackend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }





    // REGISTER USER
    public UserResponseDTO saveUser(UserRequestDTO dto) {


        User user = new User();


        user.setName(dto.getName());
        user.setEmail(dto.getEmail());


        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );


        if(dto.getRole()!=null){
            user.setRole(dto.getRole());
        }
        else{
            user.setRole(Role.USER);
        }



        User savedUser = userRepository.save(user);


        return convertToDTO(savedUser);

    }






    // LOGIN USER
    public LoginResponseDTO login(LoginRequestDTO dto){


        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );



        if(!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword()
        )){

            throw new RuntimeException("Invalid password");

        }




        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );



        LoginResponseDTO response =
                new LoginResponseDTO();



        response.setToken(token);

        response.setRole(user.getRole());



        return response;


    }







    // ADMIN CREATE USER
    public UserResponseDTO createAdminUser(User user){


        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );



        if(user.getRole()==null){

            user.setRole(Role.USER);

        }



        User savedUser =
                userRepository.save(user);



        return convertToDTO(savedUser);


    }








    // GET ALL USERS
    public List<User> getAllUsers(){


        return userRepository.findAll();


    }








    // GET USER BY ID
    public UserResponseDTO getUserById(Long id){


        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );


        return convertToDTO(user);

    }









    // UPDATE USER
    public User updateUser(
            Long id,
            User updatedUser
    ){


        User existingUser =
                userRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("User not found")
                        );



        existingUser.setName(
                updatedUser.getName()
        );


        existingUser.setEmail(
                updatedUser.getEmail()
        );



        if(updatedUser.getPassword()!=null){

            existingUser.setPassword(
                    passwordEncoder.encode(
                            updatedUser.getPassword()
                    )
            );

        }



        if(updatedUser.getRole()!=null){

            existingUser.setRole(
                    updatedUser.getRole()
            );

        }



        return userRepository.save(existingUser);


    }









    // DELETE USER
    public void deleteUser(Long id){


        userRepository.deleteById(id);


    }








    // ENTITY TO DTO CONVERTER
    private UserResponseDTO convertToDTO(User user){


        UserResponseDTO dto =
                new UserResponseDTO();



        dto.setId(user.getId());

        dto.setName(user.getName());

        dto.setEmail(user.getEmail());

        dto.setRole(user.getRole());



        return dto;


    }


}