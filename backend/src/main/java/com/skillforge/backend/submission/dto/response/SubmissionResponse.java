package com.skillforge.backend.submission.dto.response;

import com.skillforge.backend.execution.enums.Language;
import com.skillforge.backend.submission.entity.Verdict;

import java.time.LocalDateTime;

public record SubmissionResponse(
        Long id,
        String problemSlug,
        String problemTitle,
        Language language,
        Verdict verdict,
        Long executionTime,
        Long memoryUsed,
        Integer passedTestCases,
        Integer totalTestCases,
        LocalDateTime submittedAt
) {
}
