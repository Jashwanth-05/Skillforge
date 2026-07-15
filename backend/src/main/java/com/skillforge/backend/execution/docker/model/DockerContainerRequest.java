package com.skillforge.backend.execution.docker.model;

import java.util.List;

public record DockerContainerRequest(
        String image,
        List<String> command,
        List<BindMount> bindMounts,
        String workingDirectory
) {
}
