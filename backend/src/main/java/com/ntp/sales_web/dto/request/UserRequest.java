package com.ntp.sales_web.dto.request;

import java.time.LocalDateTime;

public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdDate;

    public UserRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdDate = LocalDateTime.now();
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

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
