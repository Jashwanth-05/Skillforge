package com.skillforge.backend.auth.authService;

import com.skillforge.backend.auth.dto.request.LoginRequest;
import com.skillforge.backend.auth.dto.response.LoginResponse;
import com.skillforge.backend.common.exception.EmailAlreadyExistsException;
import com.skillforge.backend.common.security.CustomUserDetails;
import com.skillforge.backend.common.security.JwtService;
import com.skillforge.backend.user.dto.request.RegisterRequest;
import com.skillforge.backend.user.dto.response.UserResponse;
import com.skillforge.backend.user.entity.Role;
import com.skillforge.backend.user.entity.User;
import com.skillforge.backend.user.mapper.UserMapper;
import com.skillforge.backend.user.userRepository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request){
        String email=request.email();
        if(userRepository.existsByEmail(email)){
           throw new EmailAlreadyExistsException();
        }
        User user=userMapper.toUser(request);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser =userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    public LoginResponse login(LoginRequest request){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                ));
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(token,"Bearer");
    }
}
