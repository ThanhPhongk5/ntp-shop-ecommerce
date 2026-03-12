package com.ntp.sales_web.mapper;

import com.ntp.sales_web.dto.response.UserResponse;
import com.ntp.sales_web.entity.User;

public class UserMapper {
    public static UserResponse ToUserRespone(User user){
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedDate());
    }
}
