package com.ntp.sales_web.service;

import com.ntp.sales_web.dto.request.UserRequest;
import com.ntp.sales_web.dto.response.UserResponse;
import com.ntp.sales_web.entity.User;
import com.ntp.sales_web.mapper.UserMapper;
import com.ntp.sales_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User addUser(UserRequest userRequest){
        User user=new User(userRequest.getUsername(),userRequest.getPassword(),userRequest.getEmail(), LocalDateTime.now());
        return userRepository.save(user);
    }
    public List<UserResponse> getUsers(){
        List<User> list= userRepository.findAll();
        return list.stream().map(UserMapper::ToUserRespone).collect(Collectors.toList());
    }
}
