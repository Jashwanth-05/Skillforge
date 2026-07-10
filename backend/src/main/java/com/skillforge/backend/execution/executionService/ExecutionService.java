package com.skillforge.backend.execution.executionService;

import com.skillforge.backend.execution.dto.request.ExecutionRequest;
import com.skillforge.backend.execution.dto.response.ExecutionResponse;

public interface ExecutionService {
    ExecutionResponse execute(ExecutionRequest request);
}
