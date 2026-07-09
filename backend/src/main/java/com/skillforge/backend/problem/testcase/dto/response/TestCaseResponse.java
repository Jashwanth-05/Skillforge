package com.skillforge.backend.problem.testcase.dto.response;

public record TestCaseResponse(
        Long id,
        String input,
        String expectedOutput,
        boolean hidden,
        String explanation
) {
}
