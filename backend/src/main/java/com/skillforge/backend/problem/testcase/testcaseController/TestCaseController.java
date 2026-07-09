package com.skillforge.backend.problem.testcase.testcaseController;

import com.skillforge.backend.problem.testcase.dto.request.CreateTestCaseRequest;
import com.skillforge.backend.problem.testcase.dto.request.UpdateTestCaseRequest;
import com.skillforge.backend.problem.testcase.dto.response.SampleTestCaseResponse;
import com.skillforge.backend.problem.testcase.dto.response.TestCaseResponse;
import com.skillforge.backend.problem.testcase.testcaseService.TestCaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class TestCaseController {
    private final TestCaseService testCaseService;

    @PostMapping("/{slug}/testcases")
    public ResponseEntity<TestCaseResponse> createTestCase(
            @PathVariable String slug,
            @Valid @RequestBody CreateTestCaseRequest request){
        TestCaseResponse response = testCaseService.createTestCase(slug,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{slug}/testcases/{id}")
    public ResponseEntity<TestCaseResponse> updateTestCase(
            @PathVariable String slug, @PathVariable Long id,
            @Valid @RequestBody UpdateTestCaseRequest request
            ){
        TestCaseResponse response = testCaseService.updateTestCase(slug,id,request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{slug}/testcases")
    public ResponseEntity<List<TestCaseResponse>> getAllTestCases(@PathVariable String slug){
        List<TestCaseResponse> responses=testCaseService.getAllTestCases(slug);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{slug}/samples")
    public ResponseEntity<List<SampleTestCaseResponse>> getAllSampleTestCases(@PathVariable String slug){
        List<SampleTestCaseResponse> responses=testCaseService.getAllSampleTestCases(slug);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{slug}/testcases/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable String slug,@PathVariable Long id){
        testCaseService.deleteTestCase(slug,id);
        return ResponseEntity.noContent().build();
    }

}
