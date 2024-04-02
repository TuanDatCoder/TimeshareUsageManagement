package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.ProjectDto;
import com.FTimeshare.UsageManagement.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "https://pass-timeshare.vercel.app")
@CrossOrigin(origins = "https://pass-timeshare-tuandat-frontends-projects.vercel.app")
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/customer/viewproject")
    public ResponseEntity<List<ProjectDto>> getAllProject() {
        List<ProjectDto> project = projectService.getAllProject();
        return ResponseEntity.ok(project);
    }
}
