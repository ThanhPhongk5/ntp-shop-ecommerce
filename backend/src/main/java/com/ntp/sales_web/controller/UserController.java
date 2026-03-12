package com.ntp.sales_web.controller;

import com.ntp.sales_web.dto.request.UserRequest;
import com.ntp.sales_web.dto.response.UserResponse;
import com.ntp.sales_web.mapper.UserMapper;
import com.ntp.sales_web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {
    private final UserService userService;
    @PostMapping("/add")
    public UserResponse add(@RequestBody UserRequest userRequest){
        return UserMapper.ToUserRespone(userService.addUser(userRequest));
    }
    @GetMapping()
    public List<UserResponse> get(){
        return userService.getUsers();
    }
}
