package com.skillforge.backend.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "name should not be empty")
        String name,

        @NotBlank
        @Email(message = "email must be a well formed email address")
        String email,

        @NotBlank
        @Size(min = 9,message = "Password should be minimum of length 9")
        String password) {
}
