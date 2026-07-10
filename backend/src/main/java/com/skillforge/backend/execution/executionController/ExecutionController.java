package com.skillforge.backend.execution.executionController;

import com.skillforge.backend.execution.dto.request.ExecutionRequest;
import com.skillforge.backend.execution.dto.response.ExecutionResponse;
import com.skillforge.backend.execution.executionService.ExecutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/execution")
@RequiredArgsConstructor
public class ExecutionController {
    private final ExecutionService executionService;

    @PostMapping("/run")
    public ResponseEntity<ExecutionResponse> run(@Valid @RequestBody ExecutionRequest request){
        return ResponseEntity.ok(executionService.execute(request));
    }

}
