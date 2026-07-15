package com.skillforge.backend.execution.docker.model;

public record DockerExecutionResult(
        String stdout,
        String stderr,
        int exitCode
) {
}
