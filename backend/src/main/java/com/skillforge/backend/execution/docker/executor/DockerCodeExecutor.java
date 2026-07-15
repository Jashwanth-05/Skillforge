package com.skillforge.backend.execution.docker.executor;

import com.skillforge.backend.execution.docker.model.DockerExecutionResult;
import com.skillforge.backend.execution.docker.model.Workspace;
import com.skillforge.backend.execution.enums.Language;

public interface DockerCodeExecutor {
    Language getLanguage();
    DockerExecutionResult execute(Workspace workspace);
}
