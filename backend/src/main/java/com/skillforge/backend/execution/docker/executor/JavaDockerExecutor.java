package com.skillforge.backend.execution.docker.executor;

import com.skillforge.backend.execution.docker.model.*;
import com.skillforge.backend.execution.docker.service.DockerContainerService;
import com.skillforge.backend.execution.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JavaDockerExecutor implements DockerCodeExecutor{

    private final DockerContainerService dockerContainerService;

    @Override
    public Language getLanguage() {
        return Language.JAVA;
    }

    @Override
    public DockerExecutionResult execute(Workspace workspace) {

        DockerContainerRequest request = new DockerContainerRequest(
                "skillforge-java",
                List.of(
                        "sh",
                        "-c",
                        "javac Main.java && java Main"
                ),
                List.of(
                        new BindMount(
                                workspace.directory(),
                                "/workspace"
                        )
                ),
                "/workspace"
        );

        String containerId = null;

        try {

            containerId = dockerContainerService.createContainer(request);

            dockerContainerService.startContainer(containerId);

            int exitCode =
                    dockerContainerService.waitForExit(containerId);

            DockerLogs logs =
                    dockerContainerService.getContainerLogs(containerId);

            return new DockerExecutionResult(
                    logs.stdout(),
                    logs.stderr(),
                    exitCode
            );

        } finally {

            if (containerId != null) {
                dockerContainerService.removeContainer(containerId);
            }

        }

    }
}
