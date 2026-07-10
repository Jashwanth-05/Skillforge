package com.skillforge.backend.execution.dto.response;

public record ExecutionResult(
        boolean success,
        boolean timedOut,
        String output,
        String error,
        Long executionTime
) {
}
