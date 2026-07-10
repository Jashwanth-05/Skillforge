package com.skillforge.backend.execution.dto.response;

public record CompilationResult(
        boolean success,
        String error
) {
}
