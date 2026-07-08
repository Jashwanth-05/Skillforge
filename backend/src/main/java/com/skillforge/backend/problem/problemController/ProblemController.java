package com.skillforge.backend.problem.problemController;

import com.skillforge.backend.problem.dto.request.CreateProblemRequest;
import com.skillforge.backend.problem.dto.request.UpdateProblemRequest;
import com.skillforge.backend.problem.dto.response.ProblemResponse;
import com.skillforge.backend.problem.problemService.ProblemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<ProblemResponse> createProblem(@Valid @RequestBody CreateProblemRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authentication = " + auth);
        System.out.println("Authorities = " + auth.getAuthorities());
        ProblemResponse response=problemService.createProblem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProblemResponse>> getAllProblems(Pageable pageable){
        Page<ProblemResponse> responses=problemService.getAllProblems(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProblemResponse> getProblem(@PathVariable String slug){
        ProblemResponse response=problemService.getProblem(slug);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ProblemResponse> updateProblem(@PathVariable String slug,@Valid @RequestBody   UpdateProblemRequest request){
        ProblemResponse response=problemService.updateProblem(slug,request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{slug}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProblem(@PathVariable String slug){
        problemService.deleteProblem(slug);
    }

}
