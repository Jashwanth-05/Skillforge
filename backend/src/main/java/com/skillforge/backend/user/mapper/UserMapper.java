package com.skillforge.backend.user.mapper;

import com.skillforge.backend.user.dto.request.RegisterRequest;
import com.skillforge.backend.user.dto.response.UserResponse;
import com.skillforge.backend.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(RegisterRequest registerRequest){
        User user=new User();
        user.setName(registerRequest.name());
        user.setEmail(registerRequest.email());
        user.setPassword(registerRequest.password());
        return user;
    }
    public UserResponse toUserResponse(User user){
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
