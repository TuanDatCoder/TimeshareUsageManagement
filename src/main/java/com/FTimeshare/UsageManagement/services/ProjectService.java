package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.ProjectDto;
import com.FTimeshare.UsageManagement.entities.ProjectEntity;
import com.FTimeshare.UsageManagement.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    public List<ProjectDto> getAllProject() {
        List<ProjectEntity> projects = projectRepository.findAll();
        return projects.stream()
                .map(projectEntity -> new ProjectDto(
                        projectEntity.getProjectID(),
                        projectEntity.getProjectName(),
                        projectEntity.getProjectDescription(),
                        projectEntity.getProjectBuildDate(),
                        projectEntity.getProjectArea(),
                        projectEntity.getProjectStatus(),
                        projectEntity.getImgName(),
                        projectEntity.getImgData(),
                        projectEntity.getContractorID().getContractorID()))
                .collect(Collectors.toList());
    }
}

