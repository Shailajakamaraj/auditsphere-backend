package com.auditsphere.auditspherebackend.service;

import com.auditsphere.auditspherebackend.util.ExcelHelper;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import com.auditsphere.auditspherebackend.dto.LoginRequestDTO;
import com.auditsphere.auditspherebackend.dto.LoginResponseDTO;
import com.auditsphere.auditspherebackend.dto.UserRequestDTO;
import com.auditsphere.auditspherebackend.dto.UserResponseDTO;

import com.auditsphere.auditspherebackend.entity.Role;
import com.auditsphere.auditspherebackend.entity.User;

import com.auditsphere.auditspherebackend.repository.UserRepository;
import com.auditsphere.auditspherebackend.jwt.JwtUtil;

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
    ){

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.jwtUtil = jwtUtil;

    }









    // ==============================
    // REGISTER USER
    // ==============================


    public UserResponseDTO saveUser(
            UserRequestDTO dto
    ){



        if(userRepository.existsByEmail(dto.getEmail())){


            throw new RuntimeException(
                    "Email already exists"
            );

        }




        User user =
                User.builder()

                        .name(
                                dto.getName()
                        )

                        .email(
                                dto.getEmail()
                        )

                        .password(
                                passwordEncoder.encode(
                                        dto.getPassword()
                                )
                        )

                        .role(
                                dto.getRole() != null
                                        ?
                                        dto.getRole()
                                        :
                                        Role.USER
                        )

                        .build();





        User savedUser =
                userRepository.save(user);




        return mapToResponse(
                savedUser
        );

    }









    // ==============================
    // LOGIN USER
    // ==============================


    public LoginResponseDTO login(
            LoginRequestDTO dto
    ){



        User user =
                userRepository
                        .findByEmail(
                                dto.getEmail()
                        )

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );







        boolean passwordMatch =
                passwordEncoder.matches(

                        dto.getPassword(),

                        user.getPassword()

                );




        if(!passwordMatch){


            throw new RuntimeException(
                    "Invalid password"
            );

        }







        String token =
                jwtUtil.generateToken(

                        user.getEmail(),

                        user.getRole().name()

                );







        return new LoginResponseDTO(

                token,

                user.getRole().name()

        );


    }









    // ==============================
    // GET ALL USERS
    // ==============================


    public List<UserResponseDTO> getAllUsers(){


        return userRepository
                .findAll()

                .stream()

                .map(this::mapToResponse)

                .toList();

    }









    // ==============================
    // GET USER BY ID
    // ==============================


    public UserResponseDTO getUserById(
            Long id
    ){


        User user =
                userRepository
                        .findById(id)

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );



        return mapToResponse(user);

    }









    // ==============================
    // DELETE USER
    // ==============================


    public void deleteUser(
            Long id
    ){


        if(!userRepository.existsById(id)){


            throw new RuntimeException(
                    "User not found"
            );

        }


        userRepository.deleteById(id);


    }









    // ==============================
    // UPDATE USER ROLE
    // ==============================


    public UserResponseDTO updateRole(
            Long id,
            Role role
    ){


        User user =
                userRepository
                        .findById(id)

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );



        user.setRole(role);



        User updated =
                userRepository.save(user);



        return mapToResponse(updated);


    }









    // ==============================
    // ENTITY -> DTO
    // ==============================


    private UserResponseDTO mapToResponse(
            User user
    ){


        UserResponseDTO dto =
                new UserResponseDTO();



        dto.setId(
                user.getId()
        );


        dto.setName(
                user.getName()
        );


        dto.setEmail(
                user.getEmail()
        );


        dto.setRole(
                user.getRole()
        );



        return dto;

    }

// ==============================
// CREATE USER BY ADMIN
// ==============================

    public UserResponseDTO createAdminUser(
            UserRequestDTO dto
    ){


        if(userRepository.existsByEmail(dto.getEmail())){

            throw new RuntimeException(
                    "Email already exists"
            );

        }



        User user =
                User.builder()

                        .name(dto.getName())

                        .email(dto.getEmail())

                        .password(
                                passwordEncoder.encode(
                                        dto.getPassword()
                                )
                        )

                        .role(
                                dto.getRole() != null
                                        ?
                                        dto.getRole()
                                        :
                                        Role.USER
                        )

                        .build();



        User savedUser =
                userRepository.save(user);



        return mapToResponse(savedUser);

    }








// ==============================
// UPDATE USER
// ==============================

    public UserResponseDTO updateUser(
            Long id,
            UserRequestDTO dto
    ){


        User user =
                userRepository
                        .findById(id)

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );



        user.setName(
                dto.getName()
        );



        user.setEmail(
                dto.getEmail()
        );




        if(dto.getPassword()!=null &&
                !dto.getPassword().isEmpty()){


            user.setPassword(
                    passwordEncoder.encode(
                            dto.getPassword()
                    )
            );

        }




        if(dto.getRole()!=null){

            user.setRole(
                    dto.getRole()
            );

        }





        User updatedUser =
                userRepository.save(user);



        return mapToResponse(updatedUser);

    }
    public void uploadUsersFromExcel(MultipartFile file) {

        try {

            List<User> users = ExcelHelper.excelToUsers(
                    file.getInputStream(),
                    passwordEncoder
            );

            List<User> usersToSave = new ArrayList<>();

            for (User user : users) {

                if (!userRepository.existsByEmail(user.getEmail())) {

                    usersToSave.add(user);

                }

            }

            userRepository.saveAll(usersToSave);

        }

        catch (IOException e) {

            throw new RuntimeException("Could not upload Excel file.");

        }

    }
}