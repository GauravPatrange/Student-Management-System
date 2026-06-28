package com.student.exception;

public class ResourseNotFoundException extends RuntimeException {
    public ResourseNotFoundException(String s) {
        super(s);
    }
}
