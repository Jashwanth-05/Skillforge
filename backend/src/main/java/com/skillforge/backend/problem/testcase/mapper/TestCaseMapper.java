package com.skillforge.backend.problem.testcase.mapper;

import com.skillforge.backend.problem.entity.TestCase;
import com.skillforge.backend.problem.testcase.dto.request.CreateTestCaseRequest;
import com.skillforge.backend.problem.testcase.dto.request.UpdateTestCaseRequest;
import com.skillforge.backend.problem.testcase.dto.response.SampleTestCaseResponse;
import com.skillforge.backend.problem.testcase.dto.response.TestCaseResponse;
import org.springframework.stereotype.Service;

@Service
public class TestCaseMapper {

    public TestCase toTestCase(CreateTestCaseRequest request){
        TestCase testCase = new TestCase();
        testCase.setInput(request.input());
        testCase.setExpectedOutput(request.expectedOutput());
        testCase.setHidden(request.hidden());
        testCase.setExplanation(request.explanation());
        return testCase;
    }

    public void updateTestCase(UpdateTestCaseRequest request,TestCase testCase){
        testCase.setInput(request.input());
        testCase.setExpectedOutput(request.expectedOutput());
        testCase.setHidden(request.hidden());
        testCase.setExplanation(request.explanation());
    }

    public TestCaseResponse toResponse(TestCase testCase){
        return new TestCaseResponse(
                testCase.getId(),
                testCase.getInput(),
                testCase.getExpectedOutput(),
                testCase.isHidden(),
                testCase.getExplanation());
    }

    public SampleTestCaseResponse toSampleResponse(TestCase testCase){
        return new SampleTestCaseResponse(
                testCase.getId(),
                testCase.getInput(),
                testCase.getExpectedOutput(),
                testCase.getExplanation()
        );
    }
}
