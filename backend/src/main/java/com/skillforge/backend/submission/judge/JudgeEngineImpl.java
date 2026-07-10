package com.skillforge.backend.submission.judge;

import com.skillforge.backend.execution.dto.response.CompilationResult;
import com.skillforge.backend.execution.dto.response.ExecutionResult;
import com.skillforge.backend.execution.enums.Language;
import com.skillforge.backend.execution.executor.CodeExecutor;
import com.skillforge.backend.execution.executor.CodeExecutorFactory;
import com.skillforge.backend.problem.entity.Problem;
import com.skillforge.backend.problem.entity.TestCase;
import com.skillforge.backend.submission.entity.Verdict;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JudgeEngineImpl implements JudgeEngine{

    private final CodeExecutorFactory executorFactory;

    public JudgeResult judge(Problem problem, String sourceCode, Language language){
        CodeExecutor executor = executorFactory.getExecutor(language);
        CompilationResult compilationResult = null;
        int totalTests = problem.getTestCases().size();
        try{
            compilationResult = executor.compile(sourceCode);
            if(!compilationResult.success()){
                return new JudgeResult(Verdict.COMPILATION_ERROR,0L,null,null, totalTests);
            }
            Long totalExecutionTime = 0L;
            int passed = 0;
            for(TestCase testCase:problem.getTestCases()){
                ExecutionResult execution = executor.run(compilationResult.compiledProgram(),testCase.getInput());
                totalExecutionTime+=execution.executionTime();
                if(execution.timedOut()){
                    return new JudgeResult(Verdict.TIME_LIMIT_EXCEEDED,totalExecutionTime,null,passed, totalTests);
                }
                if(!execution.success()){
                    return new JudgeResult(Verdict.RUN_TIME_ERROR,totalExecutionTime,null,passed, totalTests);
                }
                if(!outputsMatch(testCase.getExpectedOutput(),execution.output())){
                    return new JudgeResult(Verdict.WRONG_ANSWER,totalExecutionTime,null,passed, totalTests);
                }
                passed++;
            }
            return new JudgeResult(Verdict.ACCEPTED,totalExecutionTime,null,passed, totalTests);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }finally {
            if (compilationResult != null && compilationResult.compiledProgram() != null) {
                executor.cleanup(compilationResult.compiledProgram());
            }
        }
    }

    private boolean outputsMatch(String expected, String actual){
        return expected.trim().equals(actual.trim());
    }
}
