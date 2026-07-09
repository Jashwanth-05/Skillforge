package com.skillforge.backend.problem.testcase.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTestCaseRequest(
        @NotBlank(message = "Input is required")
        String input,
        @NotBlank(message = "Expected output is required")
        String expectedOutput,
        String explanation,
        boolean hidden
) {
}
