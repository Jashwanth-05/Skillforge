package com.skillforge.backend.user.userService;

import com.skillforge.backend.common.security.CustomUserDetails;
import com.skillforge.backend.user.dto.response.UserResponse;
import com.skillforge.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public User getAuthenticatedUser(){
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
    public UserResponse getCurrentUser(){

        User user = getAuthenticatedUser();

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
