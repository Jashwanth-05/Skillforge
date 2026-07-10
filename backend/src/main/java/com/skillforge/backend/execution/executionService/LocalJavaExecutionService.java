package com.skillforge.backend.execution.executionService;

import com.skillforge.backend.execution.dto.request.ExecutionRequest;
import com.skillforge.backend.execution.dto.response.CompilationResult;
import com.skillforge.backend.execution.dto.response.ExecutionResponse;
import com.skillforge.backend.execution.dto.response.ExecutionResult;
import com.skillforge.backend.execution.enums.ExecutionStatus;
import com.skillforge.backend.execution.util.JavaExecutionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalJavaExecutionService implements ExecutionService{
    @Override
    public ExecutionResponse execute(ExecutionRequest request) {
        Path sourceFile = null;
        try{
            sourceFile = JavaExecutionUtil.createSourceFile(request.sourceCode());
            CompilationResult compilation=JavaExecutionUtil.compile(sourceFile);
            if(!compilation.success()){
                return new ExecutionResponse(
                        ExecutionStatus.COMPILATION_ERROR,
                        "",compilation.error(),
                        null);
            }
            ExecutionResult execution = JavaExecutionUtil.run(sourceFile, request.input());

            if(execution.timedOut()){
                return new ExecutionResponse(
                        ExecutionStatus.TIME_LIMIT_EXCEEDED,
                        "",
                        execution.error(),
                        execution.executionTime());
            }
            if(!execution.success()){
                return new ExecutionResponse(
                        ExecutionStatus.RUNTIME_ERROR,
                        "",
                        execution.error(),
                        execution.executionTime());
            }
            return new ExecutionResponse(
                    ExecutionStatus.SUCCESS,
                    execution.output(),
                    execution.error(),
                    execution.executionTime());
        }catch (Exception e){
            log.error("Failed to execute Java program", e);
            throw new RuntimeException("Execution failed", e);
        }finally {
            if (sourceFile != null) {
                try {
                    JavaExecutionUtil.deleteDirectory(sourceFile.getParent());
                } catch (IOException e) {
                    log.warn("Failed to delete temp directory", e);
                }
            }
        }
    }
}
