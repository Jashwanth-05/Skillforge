package com.skillforge.backend.submission.judge;

import com.skillforge.backend.execution.enums.Language;
import com.skillforge.backend.problem.entity.Problem;

public interface JudgeEngine {
    JudgeResult judge(Problem problem, String sourceCode, Language language);
}
