package com.skillforge.backend.auth.authService;

import com.skillforge.backend.user.dto.request.RegisterRequest;
import com.skillforge.backend.user.dto.response.UserResponse;
import com.skillforge.backend.user.entity.Role;
import com.skillforge.backend.user.entity.User;
import com.skillforge.backend.user.mapper.UserMapper;
import com.skillforge.backend.user.userRepository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponse register(RegisterRequest request){
        String email=request.email();
        if(userRepository.existByEmail(email)){
           throw new RuntimeException("Email already exists");
        }
        User user=userMapper.toUser(request);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser =userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
