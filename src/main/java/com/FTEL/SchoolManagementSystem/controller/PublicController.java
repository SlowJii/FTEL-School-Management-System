package com.FTEL.SchoolManagementSystem.controller;

import com.FTEL.SchoolManagementSystem.model.Course;
import com.FTEL.SchoolManagementSystem.service.PublicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class PublicController {
    @Autowired
    private PublicService publicService;

    @GetMapping("/course-list")
    public String showAllCourses(Model model) {
        List<Course> courses = publicService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }
}
