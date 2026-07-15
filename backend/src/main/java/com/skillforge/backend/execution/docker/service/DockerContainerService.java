package com.skillforge.backend.execution.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.skillforge.backend.execution.docker.model.BindMount;
import com.skillforge.backend.execution.docker.model.DockerContainerRequest;
import com.skillforge.backend.execution.docker.model.DockerLogs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DockerContainerService {

    private final DockerClient dockerClient;

    private Bind[] toDockerBinds(List<BindMount> bindMounts) {

        if (bindMounts == null || bindMounts.isEmpty()) {
            return new Bind[0];
        }

        return bindMounts.stream()
                .map(bindMount ->
                        new Bind(
                                bindMount.hostPath().toString(),
                                new Volume(bindMount.containerPath())
                        ))
                .toArray(Bind[]::new);
    }

    public String createContainer(DockerContainerRequest request) {

        log.info("Creating container using image: {}", request.image());

        HostConfig hostConfig = HostConfig.newHostConfig()
                .withBinds(toDockerBinds(request.bindMounts()));

        CreateContainerResponse response = dockerClient
                .createContainerCmd(request.image())
                .withCmd(request.command())
                .withWorkingDir(request.workingDirectory())
                .withHostConfig(hostConfig)
                .exec();

        String containerId = response.getId();

        log.info("Container created successfully. ID={}", containerId);

        return containerId;
    }

    public void startContainer(String containerId) {

        log.info("Starting container: {}", containerId);

        dockerClient
                .startContainerCmd(containerId)
                .exec();

        log.info("Container started successfully: {}", containerId);
    }

    public Integer waitForExit(String containerId) {

        log.info("Waiting for container {} to finish...", containerId);

        Integer exitCode = dockerClient
                .waitContainerCmd(containerId)
                .start()
                .awaitStatusCode();

        log.info("Container {} exited with code {}", containerId, exitCode);

        return exitCode;
    }

    public DockerLogs getContainerLogs(String containerId) {

        log.info("Fetching logs for container {}", containerId);

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();

        try {
            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .exec(new ResultCallback.Adapter<Frame>() {

            @Override
            public void onNext(Frame frame) {

                try {

                    switch (frame.getStreamType()) {

                        case STDOUT -> stdout.write(frame.getPayload());

                        case STDERR -> stderr.write(frame.getPayload());

                        default -> { }

                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        })
        .awaitCompletion();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while reading container logs", e);
        }

        return new DockerLogs(
                stdout.toString(StandardCharsets.UTF_8),
                stderr.toString(StandardCharsets.UTF_8)
        );
    }

    public void removeContainer(String containerId) {

        log.info("Removing container {}", containerId);

        dockerClient.removeContainerCmd(containerId)
                .exec();

        log.info("Container {} removed successfully", containerId);
    }
}

