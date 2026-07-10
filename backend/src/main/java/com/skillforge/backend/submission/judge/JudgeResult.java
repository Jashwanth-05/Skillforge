package com.skillforge.backend.submission.judge;

import com.skillforge.backend.submission.entity.Verdict;

public record JudgeResult(
        Verdict verdict,
        Long executionTime,
        Long memoryUsed,
        Integer passedTestCases,
        Integer totalTestCases
) {
}
