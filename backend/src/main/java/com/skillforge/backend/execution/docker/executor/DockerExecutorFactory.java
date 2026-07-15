package com.skillforge.backend.execution.docker.executor;

import com.skillforge.backend.execution.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class DockerExecutorFactory {

    private final Map<Language, DockerCodeExecutor> executors = new EnumMap<>(Language.class);

    public DockerExecutorFactory(List<DockerCodeExecutor> executors){
        for (DockerCodeExecutor executor : executors) {
            this.executors.put(executor.getLanguage(), executor);
        }
    }

    public DockerCodeExecutor getExecutor(Language language) {

        DockerCodeExecutor executor = executors.get(language);

        if (executor == null) {
            throw new IllegalArgumentException(
                    "Unsupported language: " + language
            );
        }

        return executor;
    }
}
