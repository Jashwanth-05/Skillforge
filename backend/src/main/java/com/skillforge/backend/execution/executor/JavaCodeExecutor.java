package com.skillforge.backend.execution.executor;

import com.skillforge.backend.execution.dto.response.CompilationResult;
import com.skillforge.backend.execution.dto.response.CompiledProgram;
import com.skillforge.backend.execution.dto.response.ExecutionResult;
import com.skillforge.backend.execution.enums.Language;
import com.skillforge.backend.execution.util.JavaExecutionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
@Slf4j
public class JavaCodeExecutor implements CodeExecutor{

    @Override
    public Language getLanguage() {
        return Language.JAVA;
    }

    @Override
    public CompilationResult compile(String sourceCode) {
        try{
            Path sourceFile = JavaExecutionUtil.createSourceFile(sourceCode);
            return JavaExecutionUtil.compile(sourceFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExecutionResult run(CompiledProgram program, String input) {
        try {
            return JavaExecutionUtil.run(
                    program,
                    input
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanup(CompiledProgram program) {
        try {
            JavaExecutionUtil.deleteDirectory(
                    program.workingDirectory()
            );
        } catch (IOException e) {
            log.warn(
                    "Failed to delete temp directory",
                    e
            );
        }
    }
}
