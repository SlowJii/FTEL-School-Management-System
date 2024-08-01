package com.FTEL.SchoolManagementSystem.service;

import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PublicService {
    @Autowired
    CourseRepository courseRepository;

    // xem toan bo khoa hoc
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    // xem khoa hoc nhieu nguoi dang ki nhat
    public Course getMostPopularCourse() {
        return courseRepository.findAll().stream()
                .max(Comparator.comparingInt(course -> course.getUsers().size()))
                .orElseThrow(() -> new RuntimeException("No courses found"));
    }
}
