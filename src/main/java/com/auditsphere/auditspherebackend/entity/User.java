package com.auditsphere.auditspherebackend.entity;


import jakarta.persistence.*;
import lombok.*;

import com.auditsphere.auditspherebackend.entity.Role;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    @Column(unique = true, nullable = false)
    private String email;


    private String password;



    // ADMIN, AUDITOR, MANAGER, USER

    @Enumerated(EnumType.STRING)
    private Role role;



}