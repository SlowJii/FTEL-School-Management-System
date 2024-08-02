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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseRepository courseRepository;

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    }

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


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public void deleteUser(Long userId){
        if (!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }


    public Course createCourse(CourseRequest request){
        Course course = new Course();

        course.setCourseName(request.getCourseName());
        course.setCredit(request.getCredit());

        return courseRepository.save(course);
    }


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


    @Transactional
    public void deleteCourse(Long courseId){
        if(!courseRepository.existsById(courseId)){
            throw new RuntimeException("Course not found");
        }

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        course.getUsers().forEach(user -> user.getCourses().remove(course));
        courseRepository.delete(course);
    }


    public Course getUsersByCourseId(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    }



    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }


    public Course getMostPopularCourse() {
        return courseRepository.findAll().stream()
                .max(Comparator.comparingInt(course -> course.getUsers().size()))
                .orElseThrow(() -> new RuntimeException("No courses found"));
    }
}
