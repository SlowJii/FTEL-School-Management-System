package com.FTEL.SchoolManagementSystem.controller;

import com.FTEL.SchoolManagementSystem.dto.request.CourseRequest;
import com.FTEL.SchoolManagementSystem.dto.request.UserCreationRequest;
import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    // Create a new user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserCreationRequest request) {
        User user = adminService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // Delete a user by ID
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Create a new course
    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest request) {
        Course course = adminService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    // Update an existing course
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Course> updateCourse(@RequestBody CourseRequest request, @PathVariable Long courseId) {
        Course course = adminService.updateCourse(request, courseId);
        return ResponseEntity.ok(course);
    }

    // Delete a course by ID
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        adminService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    // Get all courses
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = adminService.getAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    // Get the most popular course
    @GetMapping("/courses/most-popular")
    public ResponseEntity<Course> getMostPopularCourse() {
        Course course = adminService.getMostPopularCourse();
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

}
