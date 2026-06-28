package com.student.services;


import com.student.dto.CourseRequestDto;
import com.student.entity.Course;


public interface CourseService {

    Course addCourse(CourseRequestDto courseRequestDto);

}
