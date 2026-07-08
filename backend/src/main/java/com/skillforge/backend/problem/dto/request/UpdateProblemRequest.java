package com.skillforge.backend.problem.dto.request;

import com.skillforge.backend.problem.entity.Difficulty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProblemRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Difficulty is required")
        Difficulty difficulty,

        @NotNull(message = "Time Limit is required")
        @Min(value = 1, message = "Time limit must be greater than 0")
        Integer timeLimit,

        @NotNull(message = "Memory Limit is required")
        @Min(value = 1, message = "Memory Limit must be greater than 0")
        Integer memoryLimit
) {
}
