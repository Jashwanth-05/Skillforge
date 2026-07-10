package com.skillforge.backend.submission.mapper;

import com.skillforge.backend.submission.dto.response.SubmissionDetailResponse;
import com.skillforge.backend.submission.dto.response.SubmissionResponse;
import com.skillforge.backend.submission.entity.Submission;
import org.springframework.stereotype.Service;

@Service
public class SubmissionMapper {

    public SubmissionResponse toResponse(Submission submission){
        return new SubmissionResponse(
                submission.getId(),
                submission.getProblem().getSlug(),
                submission.getProblem().getTitle(),
                submission.getLanguage(),
                submission.getVerdict(),
                submission.getExecutionTime(),
                submission.getMemoryUsed(),
                submission.getPassedTestCases(),
                submission.getTotalTestCases(),
                submission.getCreatedAt()
        );
    }
    public SubmissionDetailResponse toDetailResponse(Submission submission){
        return new SubmissionDetailResponse(
                submission.getId(),
                submission.getProblem().getSlug(),
                submission.getProblem().getTitle(),
                submission.getLanguage(),
                submission.getVerdict(),
                submission.getExecutionTime(),
                submission.getMemoryUsed(),
                submission.getPassedTestCases(),
                submission.getTotalTestCases(),
                submission.getSourceCode(),
                submission.getCreatedAt()
        );
    }
}
