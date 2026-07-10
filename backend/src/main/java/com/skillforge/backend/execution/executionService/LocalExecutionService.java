package com.skillforge.backend.execution.executionService;

import com.skillforge.backend.execution.dto.request.ExecutionRequest;
import com.skillforge.backend.execution.dto.response.CompilationResult;
import com.skillforge.backend.execution.dto.response.ExecutionResponse;
import com.skillforge.backend.execution.dto.response.ExecutionResult;
import com.skillforge.backend.execution.enums.ExecutionStatus;
import com.skillforge.backend.execution.executor.CodeExecutor;
import com.skillforge.backend.execution.executor.CodeExecutorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalExecutionService implements ExecutionService{

    private final CodeExecutorFactory executorFactory;

    @Override
    public ExecutionResponse execute(ExecutionRequest request) {
        CodeExecutor executor=executorFactory.getExecutor(request.language());
        CompilationResult compilation = null;
        try{
            compilation = executor.compile(request.sourceCode());
            if(!compilation.success()){
                return new ExecutionResponse(
                        ExecutionStatus.COMPILATION_ERROR,
                        "",compilation.error(),
                        null);
            }
            ExecutionResult execution = executor.run(compilation.compiledProgram(), request.input());

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
            if (compilation != null && compilation.compiledProgram() != null) {
                executor.cleanup(compilation.compiledProgram());
            }
        }
    }
}
