package com.FTEL.SchoolManagementSystem.controller;

import com.FTEL.SchoolManagementSystem.dto.request.UserUpdateRequest;
import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId, Authentication authentication) {
        String username = authentication.getName();
        User user = staffService.getUserInfo(userId, username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId, Authentication authentication) {
        String username = authentication.getName();
        try {
            User user = staffService.updateUser(request, userId, username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/courses")
    public ResponseEntity<Set<Course>> getCoursesByUserId(@PathVariable Long userId, Authentication authentication) {
        String username = authentication.getName();
        Set<Course> courses = staffService.getCoursesByUserId(userId, username);
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{userId}/addcourse/{courseId}")
    public ResponseEntity<?> addCourseToUser(@PathVariable Long userId, @PathVariable Long courseId, Authentication authentication) {
        String username = authentication.getName();
        try {
            User user = staffService.addCourseToUser(userId, courseId, username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping("/{userId}/removecourse/{courseId}")
    public ResponseEntity<?> removeCourseFromUser(@PathVariable Long userId, @PathVariable Long courseId, Authentication authentication) {
        String username = authentication.getName();
        try {
            User user = staffService.removeCourseFromUser(userId, courseId, username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
