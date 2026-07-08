package com.skillforge.backend.auth.authController;

import com.skillforge.backend.auth.authService.AuthService;
import com.skillforge.backend.auth.dto.request.LoginRequest;
import com.skillforge.backend.auth.dto.response.LoginResponse;
import com.skillforge.backend.user.dto.request.RegisterRequest;
import com.skillforge.backend.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        UserResponse response=authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response=authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
