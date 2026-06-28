package com.student.exception;

public class ResourceAlredyExistsException extends RuntimeException {
    public ResourceAlredyExistsException(String s) {
        super(s);
    }
}
