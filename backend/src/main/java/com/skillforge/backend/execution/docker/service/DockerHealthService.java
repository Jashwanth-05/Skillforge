package com.skillforge.backend.execution.docker.service;

import com.github.dockerjava.api.DockerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DockerHealthService {
    private final DockerClient dockerClient;

    public void printDockerVersion() {
        var version = dockerClient.versionCmd().exec();

        System.out.println("Docker Version : " + version.getVersion());
        System.out.println("API Version    : " + version.getApiVersion());
        System.out.println("OS             : " + version.getOperatingSystem());
        System.out.println("Architecture   : " + version.getArch());
    }
}
