package com.skillforge.backend.common.exception;

public class ProblemNotFoundException extends RuntimeException{
    public ProblemNotFoundException(){
        super("Problem Not Found");
    }
    public ProblemNotFoundException(Long id){
        super("Problem not found with id "+id);
    }
    public ProblemNotFoundException(String slug){
        super("Problem not found with slug "+slug);
    }

}
