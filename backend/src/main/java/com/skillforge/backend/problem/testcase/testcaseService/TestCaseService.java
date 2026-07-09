package com.skillforge.backend.problem.testcase.testcaseService;

import com.skillforge.backend.common.exception.TestCaseNotFoundException;
import com.skillforge.backend.problem.entity.Problem;
import com.skillforge.backend.problem.entity.TestCase;
import com.skillforge.backend.problem.problemService.ProblemService;
import com.skillforge.backend.problem.testcase.dto.request.CreateTestCaseRequest;
import com.skillforge.backend.problem.testcase.dto.request.UpdateTestCaseRequest;
import com.skillforge.backend.problem.testcase.dto.response.SampleTestCaseResponse;
import com.skillforge.backend.problem.testcase.dto.response.TestCaseResponse;
import com.skillforge.backend.problem.testcase.mapper.TestCaseMapper;
import com.skillforge.backend.problem.testcase.testcaseRepository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final TestCaseMapper testCaseMapper;
    private final ProblemService problemService;

    private TestCase findTestCase(Long id){
        return testCaseRepository.findById(id).orElseThrow(()-> new TestCaseNotFoundException("Test case not found with id "+id));
    }

    private void validateOwnerShip(Problem problem,TestCase testCase){
        if(!testCase.getProblem().getId().equals(problem.getId())){
            throw new TestCaseNotFoundException("Test case does not belong to this problem");
        }
    }

    public TestCaseResponse createTestCase(String slug, CreateTestCaseRequest request){
        Problem problem = problemService.findProblemBySlug(slug);
        TestCase testCase = testCaseMapper.toTestCase(request);
        testCase.setProblem(problem);
        TestCase savedTestCase = testCaseRepository.save(testCase);
        return testCaseMapper.toResponse(savedTestCase);
    }

    @Transactional(readOnly = true)
    public List<TestCaseResponse> getAllTestCases(String slug){
        Problem problem = problemService.findProblemBySlug(slug);
        List<TestCase> testCases = testCaseRepository.findByProblemSlug(slug);
        return testCases.stream().map(testCaseMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<SampleTestCaseResponse> getAllSampleTestCases(String slug){
        Problem problem = problemService.findProblemBySlug(slug);
        List<TestCase> testCases = testCaseRepository.findByProblemSlugAndHiddenFalse(slug);
        return testCases.stream().map(testCaseMapper::toSampleResponse).toList();
    }

    public TestCaseResponse updateTestCase(String slug,Long id, UpdateTestCaseRequest request){
        Problem problem=problemService.findProblemBySlug(slug);
        TestCase testCase=findTestCase(id);
        validateOwnerShip(problem,testCase);
        testCaseMapper.updateTestCase(request,testCase);
        return testCaseMapper.toResponse(testCase);
    }

    public void deleteTestCase(String slug,Long id){
        Problem problem=problemService.findProblemBySlug(slug);
        TestCase testCase=findTestCase(id);
        validateOwnerShip(problem,testCase);
        testCaseRepository.delete(testCase);
    }
}
