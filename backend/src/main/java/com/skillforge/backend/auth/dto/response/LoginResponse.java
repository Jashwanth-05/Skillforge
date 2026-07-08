package com.skillforge.backend.auth.dto.response;

public record LoginResponse(
        String accessToken,
        String tokenType
) {
}
