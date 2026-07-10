package com.skillforge.backend.submission.dto.request;

import com.skillforge.backend.execution.enums.Language;

public record SubmissionRequest(
        String problemSlug,
        Language language,
        String sourceCode
) {
}
