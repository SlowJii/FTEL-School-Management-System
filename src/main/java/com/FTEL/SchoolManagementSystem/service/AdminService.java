package com.FTEL.SchoolManagementSystem.service;

import com.FTEL.SchoolManagementSystem.Utils.StringUtils;
import com.FTEL.SchoolManagementSystem.dto.request.CourseRequest;
import com.FTEL.SchoolManagementSystem.dto.request.UserCreationRequest;
import com.FTEL.SchoolManagementSystem.enums.Role;
import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.repository.CourseRepository;
import com.FTEL.SchoolManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseRepository courseRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(UserCreationRequest userCreationRequest){
        User user = new User();

        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        user.setFirstName(userCreationRequest.getFirstName());
        user.setLastName(userCreationRequest.getLastName());
        user.setDob(userCreationRequest.getDob());

        String email = StringUtils.generateUserEmail(userCreationRequest.getFirstName(), userCreationRequest.getLastName());
        user.setUserEmail(email);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.STAFF.name());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long userId){
        if (!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Course createCourse(CourseRequest request){
        Course course = new Course();

        course.setCourseName(request.getCourseName());
        course.setCredit(request.getCredit());

        return courseRepository.save(course);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Course updateCourse(CourseRequest request, Long courseId){
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        if (request.getCourseName() != null) {
            course.setCourseName(request.getCourseName());
        }

        if (request.getCredit() != null) {
            course.setCredit(request.getCredit());
        }

        return courseRepository.save(course);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourse(Long courseId){
        if(!courseRepository.existsById(courseId)){
            throw new RuntimeException("Course not found");
        }

        courseRepository.deleteById(courseId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Set<User> getUsersByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        return course.getUsers();
    }

}
