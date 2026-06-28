package com.student.controllers;

import com.student.dto.ProfileUpdateRequest;
import com.student.entity.Student;
import com.student.exception.UserAuthenticationException;
import com.student.repository.StudentRepository;
import com.student.services.CourseService;
import com.student.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> updateStudentProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProfileUpdateRequest profileUpdateRequest){
        Student student = studentRepository.findByStudentCode(userDetails.getUsername())
                .orElseThrow(() -> new UserAuthenticationException("Student not found"));
        Student student1 = studentService.updateStudentProfile(student.getStudentId(), profileUpdateRequest);
        return ResponseEntity.ok().body(student1);
    }


}
