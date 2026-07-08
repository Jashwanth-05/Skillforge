package com.skillforge.backend.problem.mapper;

import com.skillforge.backend.problem.dto.request.CreateProblemRequest;
import com.skillforge.backend.problem.dto.request.UpdateProblemRequest;
import com.skillforge.backend.problem.dto.response.ProblemResponse;
import com.skillforge.backend.problem.entity.Problem;
import org.springframework.stereotype.Service;

@Service
public class ProblemMapper {
    public ProblemResponse toResponse(Problem problem){
        return new ProblemResponse(
                problem.getId(),
                problem.getTitle(),
                problem.getSlug(),
                problem.getDifficulty(),
                problem.getTimeLimit(),
                problem.getMemoryLimit());
    }
    public Problem updateProblem(CreateProblemRequest request){
        Problem problem = new Problem();
        problem.setTitle(request.title());
        problem.setDescription(request.description());
        problem.setDifficulty(request.difficulty());
        problem.setTimeLimit(request.timeLimit());
        problem.setMemoryLimit(request.memoryLimit());
        return problem;
    }
    public void updateProblem(UpdateProblemRequest request, Problem problem){
        problem.setTitle(request.title());
        problem.setDescription(request.description());
        problem.setDifficulty(request.difficulty());
        problem.setTimeLimit(request.timeLimit());
        problem.setMemoryLimit(request.memoryLimit());
    }
}
