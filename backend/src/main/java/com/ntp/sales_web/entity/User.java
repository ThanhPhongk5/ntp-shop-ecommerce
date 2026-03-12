package com.ntp.sales_web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //IDENTITY tương đương với auto_increment
    private Long id;
    private String username;

    private String password;
    private String email;
    private LocalDateTime createdDate;

    public User(String username, String password, String email, LocalDateTime createdDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
