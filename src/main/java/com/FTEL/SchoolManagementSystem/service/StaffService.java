package com.FTEL.SchoolManagementSystem.service;

import com.FTEL.SchoolManagementSystem.Utils.StringUtils;
import com.FTEL.SchoolManagementSystem.dto.request.UserUpdateRequest;
import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.repository.CourseRepository;
import com.FTEL.SchoolManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class StaffService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseRepository courseRepository;

    public User getUserInfo(Long userId, String username) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!username.equals(user.getUsername())) {
            throw new RuntimeException("You are not authorized to view this user's information");
        }
        return user;
    }

    public User updateUser(UserUpdateRequest request, Long userId, String username) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!username.equals(user.getUsername())) {
            throw new RuntimeException("You are not authorized to update this user's information");
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getDob() != null) {
            user.setDob(request.getDob());
        }

        if (request.getFirstName() != null || request.getLastName() != null) {
            String email = StringUtils.generateUserEmail(user.getFirstName(), user.getLastName());
            user.setUserEmail(email);
        }

        return userRepository.save(user);
    }

    @Transactional
    @PreAuthorize("#username == authentication.name")
    public User addCourseToUser(Long userId, Long courseId, String username) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!username.equals(user.getUsername())) {
            throw new RuntimeException("You are not authorized to add courses for this user");
        }
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        if (course.getUsers().contains(user)) {
            throw new RuntimeException("User already registered for this course!");
        }
        if (course.getSeatAvailable() <= 0) {
            throw new RuntimeException("No seats available for this course!");
        }
        course.setSeatAvailable(course.getSeatAvailable() - 1);
        user.getCourses().add(course);
        course.getUsers().add(user);
        return userRepository.save(user);
    }

    @Transactional
    @PreAuthorize("#username == authentication.name")
    public User removeCourseFromUser(Long userId, Long courseId, String username) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!username.equals(user.getUsername())) {
            throw new RuntimeException("You are not authorized to remove courses for this user");
        }
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        if (!course.getUsers().contains(user)) {
            throw new RuntimeException("User is not registered for this course!");
        }
        course.setSeatAvailable(course.getSeatAvailable() + 1);
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        return userRepository.save(user);
    }


    @PreAuthorize("#username == authentication.name")
    public Set<Course> getCoursesByUserId(Long userId, String username) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!username.equals(user.getUsername())) {
            throw new RuntimeException("You are not authorized to view this user's courses");
        }
        return user.getCourses();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
