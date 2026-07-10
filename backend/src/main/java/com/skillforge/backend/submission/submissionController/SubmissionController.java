package com.skillforge.backend.submission.submissionController;

import com.skillforge.backend.submission.dto.request.SubmissionRequest;
import com.skillforge.backend.submission.dto.response.SubmissionResponse;
import com.skillforge.backend.submission.submissionService.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<SubmissionResponse> submit(@RequestBody @Valid SubmissionRequest request){
        return ResponseEntity.ok(submissionService.submit(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponse> getSubmission(@PathVariable Long id){
        return ResponseEntity.ok(submissionService.getSubmission(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<SubmissionResponse>> getMySubmissions(Pageable pageable){
        return ResponseEntity.ok(submissionService.getMySubmissions(pageable));
    }


}
