package com.skillforge.backend.user.dto.response;

import com.skillforge.backend.user.entity.Role;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role) {
}
