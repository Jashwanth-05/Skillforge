package com.skillforge.backend.problem.entity;

import com.skillforge.backend.common.entity.BaseEntity;
import com.skillforge.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "problem")
@Getter
@Setter
@RequiredArgsConstructor
public class Problem extends BaseEntity {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false,unique = true)
    String slug;

    @Lob
    String description;

    @Enumerated(EnumType.STRING)
    Difficulty difficulty;

    @Column(nullable = false)
    Integer timeLimit;

    @Column(nullable = false)
    Integer memoryLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by",nullable = false)
    User createdBy;
}
