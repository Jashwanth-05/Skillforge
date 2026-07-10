package com.skillforge.backend.execution.executor;

import com.skillforge.backend.execution.enums.Language;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class CodeExecutorFactory {
    private final Map<Language,CodeExecutor> executors = new EnumMap<>(Language.class);

    public CodeExecutorFactory(List<CodeExecutor> codeExecutors) {
        for(CodeExecutor codeExecutor:codeExecutors){
            executors.put(codeExecutor.getLanguage(),codeExecutor);
        }
    }

    public CodeExecutor getExecutor(Language language){
        CodeExecutor executor=executors.get(language);
        if(executor==null){
            throw new IllegalArgumentException(
                    "Unsupported language: " + language
            );
        }
        return executor;
    }
}
