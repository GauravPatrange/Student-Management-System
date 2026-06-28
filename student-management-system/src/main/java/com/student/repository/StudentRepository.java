package com.student.repository;

import com.student.dto.StudentResponseDto;
import com.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentCode(String studentCode);
    boolean existsByEmail(String email);

    Optional<Student> findByStudentCode(String studentCode);
    Optional<Student> findByStudentCodeAndDateOfBirth(String studentCode, LocalDate dateOfBirth);

    List<Student> findByNameIgnoreCase(String studentName);

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.courseId = courseId")
    List<Student> findByCourseId(@Param("courseId") Long courseId);
}
