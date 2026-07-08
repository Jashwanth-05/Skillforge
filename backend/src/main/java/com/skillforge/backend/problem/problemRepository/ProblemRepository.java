package com.skillforge.backend.problem.problemRepository;

import com.skillforge.backend.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem,Long> {
    Optional<Problem> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
