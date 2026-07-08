package com.skillforge.backend.problem.dto.response;

import com.skillforge.backend.problem.entity.Difficulty;

public record ProblemResponse(
        Long id,
        String title,
        String slug,
        Difficulty difficulty,
        Integer timeLimit,
        Integer memoryLimit
) {
}
