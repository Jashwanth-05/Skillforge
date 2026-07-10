package com.skillforge.backend.execution.dto.response;

import java.nio.file.Path;

public record CompiledProgram(
        Path workingDirectory,
        String mainClass
) {
}
