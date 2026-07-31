package com.auditsphere.auditspherebackend.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginResponseDTO {


    private String token;

    private String role;



    public LoginResponseDTO(){

    }



    public LoginResponseDTO(
            String token,
            String role
    ){

        this.token = token;
        this.role = role;

    }

}