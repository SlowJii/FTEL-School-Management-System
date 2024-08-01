package com.FTEL.SchoolManagementSystem.controller;

import com.FTEL.SchoolManagementSystem.dto.request.CourseRequest;
import com.FTEL.SchoolManagementSystem.dto.request.UserCreationRequest;
import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Slf4j
@Controller
public class AdminPageController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/home")
    public String showAdminHomePage(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<User> users = adminService.getAllUsers();
        List<Course> courses = adminService.getAllCourses();
        Course mostPopularCourse = adminService.getMostPopularCourse();
        model.addAttribute("users", users);
        model.addAttribute("courses", courses);
        model.addAttribute("mostPopularCourse", mostPopularCourse);
        return "admin-homepage";
    }

    @PostMapping("/admin/createUser")
    public String createUser(@ModelAttribute UserCreationRequest request, RedirectAttributes redirectAttributes) {
        try {
            adminService.createUser(request);
            redirectAttributes.addFlashAttribute("message", "User created successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error creating user: " + e.getMessage());
        }
        return "redirect:/admin/home";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteUser(userId);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/home";
    }

    @PostMapping("/admin/createCourse")
    public String createCourse(@ModelAttribute CourseRequest request, RedirectAttributes redirectAttributes) {
        try {
            adminService.createCourse(request);
            redirectAttributes.addFlashAttribute("message", "Course created successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error creating course: " + e.getMessage());
        }
        return "redirect:/admin/home";
    }


    @GetMapping("/admin/courses/{courseId}/users")
    public String getUsersByCourseId(@PathVariable Long courseId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Set<User> users = adminService.getUsersByCourseId(courseId);
            model.addAttribute("courseUsers", users);
            return "course-users";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error retrieving users: " + e.getMessage());
            return "redirect:/admin/home";
        }
    }

    // Get all courses
    @GetMapping("/admin/courses")
    public String getAllCourses(Model model) {
        List<Course> courses = adminService.getAllCourses();
        model.addAttribute("courses", courses);
        return "admin-courses";
    }

    // Get the most popular course
    @GetMapping("/admin/courses/most-popular")
    public String getMostPopularCourse(Model model) {
        Course course = adminService.getMostPopularCourse();
        Set<User> users = course.getUsers(); // Lấy danh sách người dùng đăng ký khóa học phổ biến nhất
        model.addAttribute("mostPopularCourse", course);
        model.addAttribute("courseUsers", users); // Thêm danh sách người dùng vào model
        return "admin-most-popular-course";
    }

    // Update an existing course
    @PostMapping("/admin/updateCourse")
    public String updateCourse(@ModelAttribute CourseRequest request, @RequestParam("courseId") Long courseId, RedirectAttributes redirectAttributes) {
        try {
            adminService.updateCourse(request, courseId);
            redirectAttributes.addFlashAttribute("message", "Course updated successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error updating course: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    // Delete a course
    @PostMapping("/admin/deleteCourse")
    public String deleteCourse(@RequestParam("courseId") Long courseId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteCourse(courseId);
            redirectAttributes.addFlashAttribute("message", "Course deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }
}
