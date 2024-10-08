package com.pmt.project_management.project;

import com.pmt.project_management.user.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectMapper {

    public Project toProject(ProjectRequest request) {
        if (request == null) {
            return null;
        }
        Project project = Project.builder().id(request.getId()).name(request.getName()).description(request.getDescription()).startDate(request.getStartDate()).build();

        // Initialiser explicitement la collection members
        if (project.getMembers() == null) {
            project.setMembers(new HashSet<>());
        }

        return project;
    }

    public ProjectResponse toProjectResponse(Project project) {
        if (project == null) {
            return null;
        }
        // Mapper les membres du projet avec leur nom et rôle
        Set<ProjectMemberResponse> members = project.getMembers().stream().map(member -> {
            String roleName = member.getRoles().stream().findFirst()  // Tu peux ajuster cette partie si un membre a plusieurs rôles
                    .map(role -> role.getNom().name())  // Supposons que Role.nom soit un enum
                    .orElse("Aucun rôle");
            return ProjectMemberResponse.builder().nom(member.getNom())  // Nom du membre
                    .email(member.getEmail())
                    .role(roleName)        // Rôle du membre
                    .build();
        }).collect(Collectors.toSet());

        return ProjectResponse.builder().id(project.getId()).name(project.getName()).description(project.getDescription()).startDate(project.getStartDate()).owner(project.getOwner().getNom()).members(members).tasks(project.getTasks()).build();
    }
}