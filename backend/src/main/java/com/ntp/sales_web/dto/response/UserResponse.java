package com.ntp.sales_web.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdDate;

    public UserResponse(Long id, String username, String email, LocalDateTime createdDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdDate = createdDate;
    }
}
