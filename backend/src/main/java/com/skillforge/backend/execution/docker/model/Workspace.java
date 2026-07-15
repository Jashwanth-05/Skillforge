package com.skillforge.backend.execution.docker.model;

import java.nio.file.Path;

public record Workspace(
        Path directory,
        Path sourceFile
) {
}
