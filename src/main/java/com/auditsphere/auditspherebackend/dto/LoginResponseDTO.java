package com.auditsphere.auditspherebackend.dto;

import com.auditsphere.auditspherebackend.entity.Role;

public class LoginResponseDTO {


    private String token;

    private Role role;



    public LoginResponseDTO(){

    }



    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }



    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }

}