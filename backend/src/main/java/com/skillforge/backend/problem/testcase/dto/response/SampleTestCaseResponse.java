package com.skillforge.backend.problem.testcase.dto.response;

public record SampleTestCaseResponse(
        Long id,
        String input,
        String expectedOutput,
        String explanation
) {
}
