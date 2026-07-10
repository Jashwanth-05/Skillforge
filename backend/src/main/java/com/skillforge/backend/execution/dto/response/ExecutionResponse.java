package com.skillforge.backend.execution.dto.response;

import com.skillforge.backend.execution.enums.ExecutionStatus;

public record ExecutionResponse(
        ExecutionStatus status,
        String output,
        String error,
        Long executionTime
) {
}
