package com.skillforge.backend.submission.submissionRepository;

import com.skillforge.backend.submission.entity.Submission;
import com.skillforge.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    Page<Submission> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    Optional<Submission> findByIdAndUser(Long id, User user);
}
