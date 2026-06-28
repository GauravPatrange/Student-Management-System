package com.student.services;



import com.student.dto.ProfileUpdateRequest;
import com.student.dto.StudentRequestDto;
import com.student.dto.StudentResponseDto;
import com.student.entity.Student;

import java.util.List;

public interface StudentService {

    Student addStudent(StudentRequestDto studentRequestDto);
    List<Student> searchStudentByName(String studentName);

    void mapCourseToStudent(Long studentId, Long courseId);

    List<StudentResponseDto> getStudentByCourse(Long courseId);

    Student updateStudentProfile(Long id, ProfileUpdateRequest profileUpdateRequest);
}
