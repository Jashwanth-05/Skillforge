package com.skillforge.backend.execution.dto.request;

import com.skillforge.backend.execution.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExecutionRequest(
        @NotNull
        Language language,
        @NotBlank
        String sourceCode,
        String input
) {
}
