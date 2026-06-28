package com.student.services.impl;

import com.student.dto.CourseRequestDto;
import com.student.entity.Course;
import com.student.repository.CourseRepository;
import com.student.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public Course addCourse(CourseRequestDto courseRequestDto) {
        Course course = new Course();
        course.setCourseName(courseRequestDto.getCourseName());
        course.setDescription(courseRequestDto.getDescription());
        course.setCourseType(courseRequestDto.getCourseType());
        course.setDuration(courseRequestDto.getDuration());
        course.setTopics(courseRequestDto.getTopics());
        return courseRepository.save(course);
    }
}
