package com.skillforge.backend.execution.docker.model;

import java.nio.file.Path;

public record BindMount(
        Path hostPath,
        String containerPath
) {
}
