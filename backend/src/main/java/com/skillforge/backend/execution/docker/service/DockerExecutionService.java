package com.skillforge.backend.execution.docker.service;

import com.skillforge.backend.execution.docker.executor.DockerExecutorFactory;
import com.skillforge.backend.execution.docker.model.DockerExecutionResult;
import com.skillforge.backend.execution.docker.model.Workspace;
import com.skillforge.backend.execution.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DockerExecutionService {
    private final DockerFileService dockerFileService;
    private final DockerExecutorFactory dockerExecutorFactory;


    public DockerExecutionResult execute(
            String sourceCode,
            Language language
    ) {

        Workspace workspace =
                dockerFileService.createWorkspace(sourceCode, language);

        try {

            return dockerExecutorFactory
                    .getExecutor(language)
                    .execute(workspace);

        } finally {

            dockerFileService.deleteWorkspace(workspace);

        }

    }

}
