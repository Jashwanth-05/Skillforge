package com.skillforge.backend.problem.entity;

import com.skillforge.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "test_case")
public class TestCase extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id",nullable = false)
    private Problem problem;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String input;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String expectedOutput;


    private boolean hidden;

    @Column(columnDefinition = "TEXT")
    private String explanation;

}
