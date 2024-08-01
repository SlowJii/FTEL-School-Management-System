package com.FTEL.SchoolManagementSystem.controller;

import com.FTEL.SchoolManagementSystem.dto.request.UserUpdateRequest;
import com.FTEL.SchoolManagementSystem.model.User;
import com.FTEL.SchoolManagementSystem.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class HomePageController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/home")
    public String showHomePage(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = staffService.findByUsername(username);
        model.addAttribute("user", user);
        return "homepage";
    }

    @PostMapping("/enroll")
    public String enrollCourse(@RequestParam("courseId") Long courseId, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = staffService.findByUsername(username);
        try {
            staffService.addCourseToUser(user.getUserId(), courseId, username);
            redirectAttributes.addFlashAttribute("message", "Add Course Success");
        } catch (RuntimeException e) {
            log.error("Error enrolling course: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Error when assign course: " + e.getMessage());
        }
        return "redirect:/home";
    }


    @PostMapping("/unenroll")
    public String unenrollCourse(@RequestParam("courseId") Long courseId, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = staffService.findByUsername(username);
        try {
            staffService.removeCourseFromUser(user.getUserId(), courseId, username);
            redirectAttributes.addFlashAttribute("message", "Remove Course Success");
        } catch (RuntimeException e) {
            log.error("Error unenrolling course: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Error when remove course: " + e.getMessage());
        }
        return "redirect:/home";
    }


    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute UserUpdateRequest request, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        User user = staffService.findByUsername(username);

        try {
            staffService.updateUser(request, user.getUserId(), username);
            redirectAttributes.addFlashAttribute("message", "User updated successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error updating user: " + e.getMessage());
            return "redirect:/home";
        }
        return "redirect:/home";
    }
}
