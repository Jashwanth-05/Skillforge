package com.skillforge.backend.problem.problemService;

import com.skillforge.backend.common.exception.ProblemNotFoundException;
import com.skillforge.backend.common.util.SlugUtil;
import com.skillforge.backend.problem.dto.request.CreateProblemRequest;
import com.skillforge.backend.problem.dto.request.UpdateProblemRequest;
import com.skillforge.backend.problem.dto.response.ProblemResponse;
import com.skillforge.backend.problem.entity.Problem;
import com.skillforge.backend.problem.mapper.ProblemMapper;
import com.skillforge.backend.problem.problemRepository.ProblemRepository;
import com.skillforge.backend.user.entity.User;
import com.skillforge.backend.user.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {
    private final ProblemMapper problemMapper;
    private final ProblemRepository problemRepository;
    private final UserService userService;

    private String generateUniqueSlug(String title){
        String slug=SlugUtil.toSlug(title);
        String baseSlug=slug;
        int counter=1;
        while(problemRepository.existsBySlug(slug)){
            slug=baseSlug+"-"+counter;
            counter++;
        }
        return slug;
    }

    public ProblemResponse createProblem(CreateProblemRequest request){
        User currentUser =userService.getAuthenticatedUser();

        Problem problem=problemMapper.updateProblem(request);

        problem.setCreatedBy(currentUser);
        problem.setSlug(generateUniqueSlug(problem.getTitle()));

        Problem savedProblem=problemRepository.save(problem);

        return problemMapper.toResponse(savedProblem);
    }


    public Problem findProblemBySlug(String slug){
        return problemRepository.findBySlug(slug).orElseThrow(()->new ProblemNotFoundException(slug));
    }

    public ProblemResponse getProblem(String slug){
        return problemMapper.toResponse(findProblemBySlug(slug));
    }



    public Page<ProblemResponse> getAllProblems(Pageable pageable){
        return problemRepository.findAll(pageable).map(problemMapper::toResponse);
    }

    public ProblemResponse updateProblem(String slug, UpdateProblemRequest request){
        Problem problem = findProblemBySlug(slug);

        boolean titleChanged =
                !problem.getTitle().equals(request.title());

        problemMapper.updateProblem(request, problem);

        if (titleChanged) {
            problem.setSlug(generateUniqueSlug(problem.getTitle()));
        }

        return problemMapper.toResponse(problem);
    }
    public void deleteProblem(String slug){
        Problem problem= findProblemBySlug(slug);
        problemRepository.delete(problem);
    }


}
