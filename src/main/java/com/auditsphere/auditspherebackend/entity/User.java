package com.auditsphere.auditspherebackend.entity;


import jakarta.persistence.*;
import lombok.*;


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



    @Column(nullable=false)
    private String name;



    @Column(unique=true, nullable=false)
    private String email;



    @Column(nullable=false)
    private String password;



    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;


}