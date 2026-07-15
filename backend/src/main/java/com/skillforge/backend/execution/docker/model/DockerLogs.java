package com.skillforge.backend.execution.docker.model;

public record DockerLogs(
        String stdout,
        String stderr
) {
}
