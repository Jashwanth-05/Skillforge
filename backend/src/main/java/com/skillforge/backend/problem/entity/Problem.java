package com.skillforge.backend.problem.entity;

import com.skillforge.backend.common.entity.BaseEntity;
import com.skillforge.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problem")
@Getter
@Setter
@RequiredArgsConstructor
public class Problem extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,unique = true)
    private String slug;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(nullable = false)
    private Integer timeLimit;

    @Column(nullable = false)
    private Integer memoryLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by",nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "problem",cascade = CascadeType.ALL,orphanRemoval = true)
    @OrderBy("id ASC")
    private List<TestCase> testCases = new ArrayList<>();

    public void addTestCase(TestCase testCase){
        testCases.add(testCase);
        testCase.setProblem(this);
    }

    public void removeTestCase(TestCase testCase){
        testCases.remove(testCase);
        testCase.setProblem(null);
    }
}
