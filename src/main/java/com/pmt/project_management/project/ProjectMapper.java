package com.pmt.project_management.project;

import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {
    public Project toProject(ProjectRequest request) {
        return Project.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .build();
    }
}
