package com.skillforge.backend.execution.executor;

import com.skillforge.backend.execution.dto.response.CompilationResult;
import com.skillforge.backend.execution.dto.response.CompiledProgram;
import com.skillforge.backend.execution.dto.response.ExecutionResult;
import com.skillforge.backend.execution.enums.Language;

public interface CodeExecutor {
    Language getLanguage();

    CompilationResult compile(String sourceCode);

    ExecutionResult run(CompiledProgram program,String input);

    void cleanup(CompiledProgram program);
}
