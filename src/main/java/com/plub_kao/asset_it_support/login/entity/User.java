package com.plub_kao.asset_it_support.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 64)
    private String password; // จะเก็บ hashed

    @Column(name = "user_role", nullable = false)
    private String userRole; // "ADMIN" or "STAFF"


}