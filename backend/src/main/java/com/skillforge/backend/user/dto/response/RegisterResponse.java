package com.skillforge.backend.user.dto.response;

public record RegisterResponse(
        Long id,
        String name,
        String email,
        String role) {
}
