package com.skillforge.backend.submission.submissionService;

import com.skillforge.backend.common.exception.SubmissionNotFoundException;
import com.skillforge.backend.problem.entity.Problem;
import com.skillforge.backend.problem.problemService.ProblemService;
import com.skillforge.backend.submission.dto.request.SubmissionRequest;
import com.skillforge.backend.submission.dto.response.SubmissionResponse;
import com.skillforge.backend.submission.entity.Submission;
import com.skillforge.backend.submission.judge.JudgeEngineImpl;
import com.skillforge.backend.submission.judge.JudgeResult;
import com.skillforge.backend.submission.mapper.SubmissionMapper;
import com.skillforge.backend.submission.submissionRepository.SubmissionRepository;
import com.skillforge.backend.user.entity.User;
import com.skillforge.backend.user.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final ProblemService problemService;
    private final SubmissionRepository submissionRepository;
    private final JudgeEngineImpl judgeEngine;
    private final UserService userService;
    private final SubmissionMapper submissionMapper;

    public SubmissionResponse submit(SubmissionRequest request){
        User user=userService.getAuthenticatedUser();
        Problem problem=problemService.findProblemBySlug(request.problemSlug());
        JudgeResult judgeResult = judgeEngine.judge(problem,request.sourceCode(),request.language());

        Submission submission = new Submission();
        submission.setProblem(problem);
        submission.setLanguage(request.language());
        submission.setVerdict(judgeResult.verdict());
        submission.setUser(user);
        submission.setSourceCode(request.sourceCode());
        submission.setExecutionTime(judgeResult.executionTime());
        submission.setPassedTestCases(judgeResult.passedTestCases());
        submission.setTotalTestCases(judgeResult.totalTestCases());
        submission.setMemoryUsed(judgeResult.memoryUsed());
        submission.setMemoryUsed(null);

        Submission savedSubmission = submissionRepository.save(submission);

        return submissionMapper.toResponse(savedSubmission);
    }

    public SubmissionResponse getSubmission(Long submissionId){
        User user=userService.getAuthenticatedUser();
        Submission submission = submissionRepository
                .findByIdAndUser(submissionId,user)
                .orElseThrow(()->new SubmissionNotFoundException("Submission not found with id "+submissionId));
        return submissionMapper.toResponse(submission);
    }

    public List<SubmissionResponse> getMySubmissions(Pageable pageable){
        User user =userService.getAuthenticatedUser();
        return submissionRepository.findByUserOrderByCreatedAtDesc(user,pageable)
                .stream()
                .map(submissionMapper::toResponse)
                .toList();
    }
}
