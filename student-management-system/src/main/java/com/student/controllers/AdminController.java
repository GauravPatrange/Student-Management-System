package com.student.controllers;

import com.student.dto.CourseRequestDto;
import com.student.dto.StudentRequestDto;
import com.student.dto.StudentResponseDto;
import com.student.entity.Course;
import com.student.entity.Student;
import com.student.services.CourseService;
import com.student.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public AdminController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    //Student API's

    @PostMapping("/student")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody StudentRequestDto studentRequestDto){

        Student student = studentService.addStudent(studentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @GetMapping("/student/search")
    public ResponseEntity<List<Student>> searchStudent(@RequestParam String name){

        List<Student> students = studentService.searchStudentByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @GetMapping("/student/by-course/{courseId}")
    public ResponseEntity<List<StudentResponseDto>> getStudentByCourse(@PathVariable Long courseId){
        List<StudentResponseDto> studentByCourse = studentService.getStudentByCourse(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(studentByCourse);
    }

    //Course API's

    @PostMapping("/course")
    public ResponseEntity<Course> addCourse(@RequestBody CourseRequestDto courseRequestDto){
        Course course = courseService.addCourse(courseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    //Student Courser Mapping

    @PostMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<String> mapStudentCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        studentService.mapCourseToStudent(studentId,courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course assign to student");
    }

}
