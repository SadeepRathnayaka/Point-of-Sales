package com.point_of_sales.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)   // Store roles in String type in the DB instead of default type integer
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "otp", nullable = true)
    private String otp;

    @Column(name = "otp_expire_date", nullable = true)
    private LocalDateTime otpExpireDate;

    public User(String userName, String email, UserRole role, String password) {
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.password = password;
    }
}

