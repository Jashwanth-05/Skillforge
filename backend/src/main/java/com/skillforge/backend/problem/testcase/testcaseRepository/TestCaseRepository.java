package com.skillforge.backend.problem.testcase.testcaseRepository;

import com.skillforge.backend.problem.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase,Long> {
    List<TestCase> findByProblemId(Long id);
    List<TestCase> findByProblemIdAndHiddenFalse(Long id);
    List<TestCase> findByProblemSlug(String slug);
    List<TestCase> findByProblemSlugAndHiddenFalse(String slug);

}
